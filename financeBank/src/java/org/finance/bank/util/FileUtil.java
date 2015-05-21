package org.finance.bank.util;

/**
 *
 * @author oscar
 */
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.finance.bank.model.service.FileFunction;
import java.lang.reflect.Method;
import java.util.Random;

public class FileUtil {

    private static Log log = LogFactory.getLog(FileUtil.class);
    public static int BUFFER_SIZE = 10240;
    public static char SEPARATOR = '/';
    public String targetFile = "";
    public String ftpStartTime = "";
    public String ftpEndTime = "";
    public String servletStartTime = "";
    public String servletEndTime = "";
    protected static FileFilter imageFileFilter = null;
    protected static FileFilter directoryFilter = null;
    protected static FileFilter regularFileFilter = null;
    private static Perl5Util perl5Util = new Perl5Util();

    /**
     * This function visits all files in dir, and runs function func
     * on them if and only if filter accepts the file.  It then
     * recursively processes all subdirectories of dir.
     */
    public static void recursiveDirWalk(File dir, FileFunction func, FileFilter filter) {
        File[] files = dir.listFiles(filter);
        for (int i = 0; i < files.length; i++) {
            log.debug("Processing file " + files[i]);
            func.exec(files[i]);
        }
        File[] dirs = dir.listFiles(new DirectoryFilter());
        for (int i = 0; i < dirs.length; i++) {
            log.debug("Processing dir " + dirs[i]);
            recursiveDirWalk(dirs[i], func, filter);
        }
    }

    /**
     * equivalent to readToArray(new BufferedReader(new FileReader(f))
     */
    public static String[] readToArray(File f) {
        try {
            return readToArray(new BufferedReader(new FileReader(f)));
        } catch (IOException e) {
            log.error("readToArray: IOException opening file " + f, e);
            return null;
        }
    }

    /** Reads the contents of the given BufferedReader by making each
     *  line an entry in the returned array of Strings, otherwise
     *  returnining null and logging errors.  Skips lines of only
     *  whitespace.  Warning: this is slow and memory intensize, only
     *  for small files.
     */
    public static String[] readToArray(BufferedReader reader) {
        try {
            String line;
            ArrayList lineList = new ArrayList();
            while ((line = reader.readLine()) != null) {
                if ((line.length() != 0) && (line.trim().length() != 0)) {
                    lineList.add(line);
                }
            }
            return (String[]) lineList.toArray(new String[lineList.size()]);
        } catch (IOException e) {
            log.error("readToArray: IOException reading from " + reader, e);
            return null;
        }
    }

    /**
     *     equivalent to readFromBufferedReader(new BufferedReader(new FileReader(f)));
     */
    public static StringBuffer readFromFile(File f) {
        try {
            return readFromBufferedReader(new BufferedReader(new FileReader(f)));
        } catch (IOException e) {
            log.error("readFromURL: IOException opening file " + f, e);
            return null;
        }
    }

    /** Reads the contents of the given BufferedReader if possible and returns
     *     contents as a string, otherwise returnining null and logging
     *     errors.
     */
    public static StringBuffer readFromBufferedReader(BufferedReader reader) {
        try {
            String line;
            StringBuffer contents = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                contents.append(line);
                contents.append(StringUtil.newline);
            }
            return contents;
        } catch (IOException e) {
            log.error("readFromBufferedReader: IOException reading from " + reader, e);
            return null;
        }
    }

    /** So this doesn't really belong here, but oh well. */
    public static StringBuffer readFromURL(URL url) {
        if (url == null) {
            log.error("Called readFromURL with null url, returning null.");
            return null;
        }
        try {
            return readFromBufferedReader(new BufferedReader(new InputStreamReader(url.openStream())));
        } catch (IOException e) {
            log.error("readFromURL: IOException opening URL " + url, e);
            return null;
        }
    }

    /** Gets a relative path to target for a file sitting in crntDir.
     *     May return null if a getCanonicalPath fails internally or if
     *     there is some other error.
     */
    public static String makeRelativePath(File crntDir, File target) {
        log.debug("makeRealtivePath" + StringUtil.newline
                + "crntDir=" + crntDir + StringUtil.newline
                + "target=" + target + StringUtil.newline);
        File absCrnt = null;
        File absTarg = null;
        try {
            absCrnt = crntDir.getCanonicalFile();
            absTarg = target.getCanonicalFile();
        } catch (IOException e) {
            log.error("Couldn't get canonical file", e);
            return null;
        }
        log.debug("makeRealtivePath" + StringUtil.newline
                + "abs crntDir=" + absCrnt + StringUtil.newline
                + "abs target=" + absTarg + StringUtil.newline);
        List clist = splitFile(absCrnt);
        List tlist = splitFile(absTarg);
        Iterator crnt = clist.iterator();
        Iterator targ = tlist.iterator();
        String sep = File.separator;
        StringBuffer relPath = new StringBuffer();
        while (crnt.hasNext() && targ.hasNext()) {
            String c = (String) crnt.next();
            String t = (String) targ.next();
            if (c.equals(t)) {
                log.debug("Removing common dir: " + c);
                crnt.remove();
                targ.remove();
            } else {
                break;
            }
        }
        crnt = clist.iterator();
        targ = tlist.iterator();
        while (crnt.hasNext()) {
            String s = (String) crnt.next();
            log.debug("Adding a .. for " + s);
            relPath.append("..");
            relPath.append(sep);
        }
        while (targ.hasNext()) {
            String s = (String) targ.next();
            log.debug("Adding path " + s);
            relPath.append(s);
            if (targ.hasNext()) {
                relPath.append(sep);
            }
        }
        log.debug("Generated path = " + relPath);
        return relPath.toString();
    }

    /** Splits the file on the filename seperator, returning a List of
    the corresponding names.  That is, "/usr/local/bin/" becomes
    ["usr", "local", "bin"].  There will be no empty or null
    strings in the output.

    <p>XXX Java's get absolute path does not appear to remove
    "."'s from a path, ie, a File foo = "./output/", which is
    really, "/home/mcmahan/output/", gets an absolute path of
    "/home/mcmahan/./output/", which may work, but doesn't do what
    we want here.  Using get Canonical Path first seems to be a
    solution.
     */
    public static List splitFile(String f) {
        List results = new LinkedList();
        String sep = File.separator;
        String pattern = "m#" + sep + "#";
        perl5Util.split(results, pattern, f);
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            String s = (String) iter.next();
            if ((s == null) || (s.equals(""))) {
                iter.remove();
            }
        }
        return results;
    }

    /**
    Equivalent to splitFile(f.getPath());
    See comments for splitFile(String f)
     */
    public static List splitFile(File f) {
        log.debug("Splitting " + f.getPath());
        return splitFile(f.getPath());
    }

    public static FileFilter getDirectoryFilter() {
        if (directoryFilter == null) {
            directoryFilter = new DirectoryFilter();
        }
        return directoryFilter;
    }

    public static FileFilter getImageFileFilter() {
        if (imageFileFilter == null) {
            imageFileFilter = new ValidImageFileFilter();
        }
        return imageFileFilter;
    }

    public static FileFilter getRegularFileFilter() {
        if (regularFileFilter == null) {
            regularFileFilter = new RegularFileFilter();
        }
        return regularFileFilter;
    }

    public static File getCWD() {
        return new File(System.getProperty("user.dir"));
    }

    private static String getUniqueId() {
        String strUniqueId = "";
        Date date = new Date();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        strUniqueId = simpledateformat.format(date) + (int) (Math.ceil(Math.random() * 100000));
        return strUniqueId;
    }

    public static String getUniqueFolderId() {
        String strUniueId = getUniqueId();
        String strDateId = "";
        String strTimeId = "";
        String strFolderId = null;
        strDateId = strUniueId.substring(0, 6) + SEPARATOR;
        strTimeId = strUniueId.substring(6, 8) + SEPARATOR;
        strFolderId = strDateId + strTimeId + strUniueId;
        return strFolderId;
    }

    /** Accept only files that end in a valid image file format and
    exist. */
    private static class ValidImageFileFilter
            implements FileFilter {

        String[] validExt = {".jpg", ".jpeg", ".gif", ".png"};

        public boolean accept(File file) {
            boolean valid = false;
            String name = file.getName();
            if (!file.exists() || !file.isFile()) {
                valid = false;
            } else if (name == null || name.equals("")) {
                valid = false;
            } else {
                for (int i = 0; i < validExt.length; i++) {
                    if (name.toLowerCase().endsWith(validExt[i])) {
                        valid = true;
                    }
                }
            }
            if (valid) {
                log.debug("Accepting image " + file);
                return true;
            } else {
                log.debug("Rejecting image " + file);
                return false;
            }
        }
    }

    private static class DirectoryFilter
            implements FileFilter {

        public boolean accept(File file) {
            return file.isDirectory();
        }
    }

    private static class RegularFileFilter
            implements FileFilter {

        public boolean accept(File file) {
            boolean isFile = file.isFile();
            if (isFile) {
                log.debug("Accepting " + file);
            } else {
                log.debug("Rejecting " + file);
            }
            return isFile;
        }
    }
    // a new File("...")'s path is not canonicalized, only resolved
    // and normalized (e.g. redundant separator chars removed),
    // so as of JDK 1.4.2, this is a valid test for case insensitivity,
    // at least when it is assumed that we are dealing with a configuration
    // that only needs to consider the host platform's native file system,
    // even if, unlike for File.getCanonicalPath(), (new File("a")).exists() or
    // (new File("A")).exits(), regardless of the hosting system's
    // file path case sensitivity policy.
    public static final boolean fsIsIgnoreCase =
            (new File("A")).equals(new File("a"));
    // posix separator normalized to File.separator?
    // CHECKME: is this true for every file system under Java?
    public static final boolean fsNormalizesPosixSeparator =
            (new File("/")).getPath().endsWith(File.separator);
    // only available in JDK 1.2 or better
    static final Method deleteOnExitMethod = getDeleteOnExitMethod();
    // for JDK 1.1 createTempFile
    static final Random random = new Random(System.currentTimeMillis());

    // retrieve the method, or null of not available
    private static Method getDeleteOnExitMethod() {
        try {
            return File.class.getMethod("deleteOnExit", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete the named file
     */
    public static void delete(String filename) throws IOException {
        try {
            (new File(filename)).delete();
        } catch (Throwable e) {
            throw toIOException(e);
        }
    }

    /**
     * Requests, in a JDK 1.1 compliant way, that the file or directory denoted
     * by the given abstract pathname be deleted when the virtual machine
     * terminates.
     *
     * Deletion will be attempted only for JDK 1.2 and greater runtime
     * environments and only upon normal termination of the virtual
     * machine, as defined by the Java Language Specification.
     *
     * Once deletion has been sucessfully requested, it is not possible to
     * cancel the request. This method should therefore be used with care.
     *
     * @param f the abstract pathname of the file be deleted when the virtual
     * machine terminates */
    public static void deleteOnExit(File f) {
        if (deleteOnExitMethod == null) {
        } else {
            try {
                deleteOnExitMethod.invoke(f, new Object[0]);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Return true or false based on whether the named file exists. */
    static public boolean exists(String filename) throws IOException {
        try {
            return (new File(filename)).exists();
        } catch (Throwable e) {
            throw toIOException(e);
        }
    }

    /**
     * Rename the file with oldname to newname. If a file with oldname does not
     * exist, nothing occurs. If a file with newname already exists, it is
     * deleted it before the renaming operation proceeds. */
    static public void renameOverwrite(String oldname, String newname) throws IOException {
        try {
            if (exists(oldname)) {
                delete(newname);
                File file = new File(oldname);
                file.renameTo(new File(newname));
            }
        } catch (Throwable e) {
            throw toIOException(e);
        }
    }

    static IOException toIOException(Throwable e) {
        if (e instanceof IOException) {
            return (IOException) e;
        } else {
            return new IOException(e.getMessage());
        }
    }

    /**
     * Retrieves the absolute path, given some path specification.
     *
     * @param path the path for which to retrieve the absolute path
     * @return the absolute path */
    public static String absolutePath(String path) {
        return (new File(path)).getAbsolutePath();
    }

    /**
     * Retrieves the canonical file for the given file, in a
     * JDK 1.1 complaint way. *
     * @param f the File for which to retrieve the absolute File
     * @return the canonical File */
    public static File canonicalFile(File f) throws IOException {
        return new File(f.getCanonicalPath());
    }

    /**
     * Retrieves the canonical file for the given path, in a
     * JDK 1.1 complaint way.
     *
     * @param path the path for which to retrieve the canonical File
     * @return the canonical File */
    public static File canonicalFile(String path) throws IOException {
        return new File(new File(path).getCanonicalPath());
    }

    /** * Retrieves the canonical path for the given File, in a
     * JDK 1.1 complaint way.
     *
     * @param f the File for which to retrieve the canonical path
     * @return the canonical path */
    public static String canonicalPath(File f) throws IOException {
        return f.getCanonicalPath();
    }

    /**
     * Retrieves the canonical path for the given path, in a
     * JDK 1.1 complaint way.
     *
     * @param path the path for which to retrieve the canonical path
     * @return the canonical path */
    public static String canonicalPath(String path) throws IOException {
        return new File(path).getCanonicalPath();
    }

    /**
     * Retrieves the canonical path for the given path, or the absolute
     * path if attemting to retrieve the canonical path fails.
     *
     * @param path the path for which to retrieve the canonical or
     * absolute path
     * @return the canonical or absolute path */
    public static String canonicalOrAbsolutePath(String path) {
        try {
            return canonicalPath(path);
        } catch (Exception e) {
            return absolutePath(path);
        }
    }

    /**
     * Calls writeToFile with createDir flag false.
     *
     * @see writeToFile(String fileName, InputStream iStream, boolean createDir)
     * 
     */
    public static void writeToFile(String fileName, InputStream iStream)
            throws IOException {
        writeToFile(fileName, iStream, false);
    }

    /**
     * Writes InputStream to a given <code>fileName<code>.
     * And, if directory for this file does not exists,
     * if createDir is true, creates it, otherwice throws OMDIOexception.
     *
     * @param fileName - filename save to.
     * @param iStream  - InputStream with data to read from.
     * @param createDir (false by default)
     * @throws IOException in case of any error.
     *
     */
    public static void writeToFile(String fileName, InputStream iStream,
            boolean createDir)
            throws IOException {
        String me = "FileUtils.WriteToFile";
        if (fileName == null) {
            throw new IOException(me + ": filename is null");
        }
        if (iStream == null) {
            throw new IOException(me + ": InputStream is null");
        }
        File theFile = new File(fileName);
        if (theFile.exists()) {
            String msg =
                    theFile.isDirectory() ? "directory" : (!theFile.canWrite() ? "not writable" : null);
            if (msg != null) {
                throw new IOException(me + ": file '" + fileName + "' is " + msg);
            }
        }
        if (createDir && theFile.getParentFile() != null) {
            theFile.getParentFile().mkdirs();
        }
        BufferedOutputStream fOut = null;
        try {
            fOut = new BufferedOutputStream(new FileOutputStream(theFile));
            byte[] buffer = new byte[32 * 1024];
            int bytesRead = 0;
            while ((bytesRead = iStream.read(buffer)) != -1) {
                fOut.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new IOException(me + " failed, got: " + e.toString());
        } finally {
            close(iStream, fOut);
        }
    }

    /**
     * Closes InputStream and/or OutputStream.
     * It makes sure that both streams tried to be closed,
     * even first throws an exception.
     *
     * @throw IOException if stream (is not null and) cannot be closed.
     *
     */
    protected static void close(InputStream iStream, OutputStream oStream)
            throws IOException {
        try {
            if (iStream != null) {
                iStream.close();
            }
        } finally {
            if (oStream != null) {
                oStream.close();
            }
        }
    }
    private static final int RND_FILENAME_FACTOR = 100000;

    /**
     * Only utility functions -- no instances allowed.
     */
    /**
     * Copies the file or directory <code>src</code> to the
     * directory <code>destinationDir</code>.
     *
     * @param src the source file or directory.
     * @param destinationDir the destination directory.
     * @throws IOException in case of an I/O error.
     */
    public static void copy(File src, File destinationDir)
            throws IOException {
        if (src.isFile()) {
            copyFile(src, destinationDir);
        } else if (src.isDirectory()) {
            final File subdir = new File(destinationDir, src.getName());
            if (!subdir.exists()) {
                if (!subdir.mkdir()) {
                    throw new IOException("Failed to create subdir '" + subdir + "'.");
                }
            }
            final File[] files = src.listFiles();
            for (int i = 0; i < files.length; i++) {
                copy(files[i], subdir);
            }
        }
    }

    /**
     * Copies the file <code>src</code> to the file or directory
     * <code>dest</code>.
     *
     * @param src The source file.
     * @param dest The destination file or directory.
     * @throws FileNotFoundException if the source file does not exists.
     * @throws IOException in case of an I/O error.
     */
    public static void copyFile(File src, File dest)
            throws FileNotFoundException, IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(src);
            if (dest.isDirectory()) {
                out = new FileOutputStream(new File(dest, src.getName()));
            } else {
                out = new FileOutputStream(dest);
            }
            copy(in, out);
        } finally {
            close(in);
            close(out);
        }
    }

    /**
     * Copy from an input stream to an output stream.
     *
     * @param in The input stream.
     * @param out The output stream.
     * @throws IOException when an error happens during a read or a write
     *       operation.
     */
    public static void copy(InputStream in, OutputStream out)
            throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /**
     * Copy all files under <code>srcDir</code> to the directory
     * <code>dst</code>.
     *
     * Unix command:
     * <pre>
     * $ cp "$scrDir/*" "$dst"
     * </pre>
     *
     * @param srcDir the source directory.
     * @param dst the destination directory.
     * @throws IOException in case of an I/O error.
     */
    public static void copySlashStar(File srcDir, File dst)
            throws IOException {
        if (srcDir.isDirectory()) {
            final File[] files = srcDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                copy(files[i], dst);
            }
        } else {
            throw new IllegalArgumentException("Souce must be a directory. ('" + srcDir + "')");
        }
    }

    /**
     * Creates a temporary directory.
     * @param baseDir the base directory.
     * @param prefix prefix for the temporary directory.
     * @return a temporary directory.
     * @throws IOException in case of an I/O error.
     */
    public static File createTempDir(File baseDir, String prefix)
            throws IOException {
        final String dirname = prefix + String.valueOf((int) (Math.random() * RND_FILENAME_FACTOR));

        final File tempDir = new File(baseDir, dirname);

        if (!tempDir.mkdir()) {
            throw new IOException("Cannot create temp directory '" + tempDir + "'");
        }
        return tempDir;
    }

    /**
     * Closes the input stream (safe).
     *
     * This method tries to close the given input stream and
     * if an IOException occurs a message with the level
     * {@link Level#FINE} is logged. It's safe to pass a
     * <code>null</code> reference for the argument.
     *
     * @param in the input stream that should be closed.
     * @deprecated use IoUtil.close(InputStream)
     */
    public static void close(InputStream in) {
//        IoUtil.close(in);
        if (in != null) {
            try {
                in.close();
            } catch (IOException x) {
                log.error("Array: IOException from " + in, x);
            }
        }
    }

    /**
     * Closes the output stream (safe).
     *
     * This method tries to close the given output stream and
     * if an IOException occurs a message with the level
     * {@link Level#FINE} is logged. It's safe to pass a
     * <code>null</code> reference for the argument.
     *
     * @param out the output stream that should be closed.
     * @deprecated use IoUtil.close(OutputStream)
     */
    public static void close(OutputStream out) {

        if (out != null) {
            try {
                out.close();
            } catch (IOException x) {
                log.error("Array: IOException from " + out, x);
            }
        }
    }

    /**
     * Closes the reader (safe).
     *
     * This method tries to close the given reader and if an IOException occurs
     * a message with the level {@link Level#FINE} is logged. It's safe
     * to pass a <code>null</code> reference for the argument.
     *
     * @param reader the reader that should be closed.
     * @deprecated use IoUtil.close(Reader)
     */
    public static void safeClose(Reader reader) {

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException x) {
                log.error("Array: IOException from " + reader, x);
            }
        }
    }

    /**
     * Closes the writer (safe).
     *
     * This method tries to close the given writer and if an IOException occurs
     * a message with the level {@link Level#FINE} is logged. It's safe
     * to pass a <code>null</code> reference for the argument.
     *
     * @param writer the writer that should be closed.
     * @deprecated use IoUtil.close(Writer)
     */
    public static void safeClose(Writer writer) {

        if (writer != null) {
            try {
                writer.close();
            } catch (IOException x) {
                log.error("Array: IOException from " + writer, x);
            }
        }
    }

    /**
     * Search for files in a directory hierarchy.
     *
     * Unix command:
     * <pre>
     * find path -name pattern
     * </pre>
     * @param path root directory.
     * @param pattern filename pattern.
     * @return a list of files matching the given <code>pattern</code>
     *         under <code>path</code>.
     */
    public static List findFile(File path, String pattern) {
        final List ret = new ArrayList();

        // Check whether the path exists
        if (!path.exists()) {
            throw new IllegalArgumentException(
                    "The specified path does not exist! ('" + path + "')");
        }

        findFile(path, pattern, ret);

        return ret;
    }

    private static void findFile(File file, String pattern, List found) {
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                findFile(files[i], pattern, found);
            }
        } else {
            if (file.getName().matches(pattern)) {
                found.add(file);
            }
        }
    }

    /**
     * Remove file or directory.
     *
     * Unix command:
     * <pre>
     * rm -rf file
     * </pre>
     * @param file the file or directory to delete.
     * @throws IOException in case of an I/O error.
     */
    public static void rmdir(File file)
            throws IOException {
        if (file == null) {
        } else if (file.isDirectory()) {
            final File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                rmdir(files[i]);
            }
            if (!file.delete()) {
                throw new IOException("Failed to delete directory " + file + ".");
            }
        } else {
            if (!file.delete()) {
                throw new IOException("Failed to delete file " + file + ".");
            }
        }
    }

    /**
     * Returns the relative path of <code>file</code> to the file
     * <code>basedir</code>.
     * @param baseDir the base directory or file.
     * @param file the file.
     * @return the relative path of the file to the basedir.
     * @throws IOException in case of an I/O error.
     */
    public static String getRelativePath(File baseDir, File file)
            throws IOException {
        final String base = baseDir.getCanonicalPath();
        String fileName = file.getCanonicalPath();

        if (fileName.startsWith(base)) {
            fileName = fileName.substring(base.length());
            if (fileName.charAt(0) == '/') {
                fileName = fileName.substring(1);
            }
        } else {
            throw new RuntimeException("Cannot add file '" + file + "' with different baseDir '" + baseDir + "'.");
        }
        return fileName;
    }

    /**
     * Renames the file <code>aFile</code>.
     *
     * @param  aFile The file to be renamed.
     * @param  dest  The new abstract pathname for the named file
     * @throws IOException if the the renaming was not successful.
     */
    public static void rename(File aFile, File dest)
            throws IOException {
        if (!aFile.renameTo(dest)) {
            throw new IOException("Failed to rename " + aFile + " to " + dest);
        }
    }

    /**
     * Deletes the given file <code>aFile</code>.
     *
     * @param  aFile The file to be deleted.
     * @throws IOException if the the deletion was not successful.
     */
    public static void delete(File aFile)
            throws IOException {
        if (aFile.exists()) {
            if (!aFile.delete()) {
                throw new IOException("Failed to delete " + aFile);
            }
        }
    }

    /**
     * Creates the given directories.
     *
     * @param dirs the directories to be created.
     * @throws IOException the directories could not be created.
     * @see File#mkdirs()
     */
    public static void mkdirs(File dirs)
            throws IOException {
        if (!dirs.exists() || !dirs.isDirectory()) {
            if (!dirs.mkdirs()) {
                throw new IOException("Failed to create directories " + dirs);
            }
        }
    }

    /**
     * Creates the given directory.
     *
     * @param dir the directory to be created.
     * @throws IOException if the file could not be created.
     * @see File#mkdir()
     */
    public static void mkdir(File dir)
            throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            if (!dir.mkdir()) {
                throw new IOException("Failed to create directory " + dir);
            }
        }
    }

    /**
     * Creates the given file.
     * @param newFile the file to create
     * @throws IOException the file could not be created.
     * @see File#createNewFile()
     */
    public static void createNewFile(File newFile)
            throws IOException {
        if (!newFile.createNewFile()) {
            throw new IOException("Failed to create new File " + newFile);
        }
    }

    /** * Lee el archivo de texto caracter por caracter *
     * @param f Un objeto File con el archivo que se desea leer
     * @throws java.io.FileNotFoundException En caso de que el archivo no exista
     * el metodo lanza esta excepcion
     * @throws java.io.IOException En caso de que el archivo no se pueda leer se
     * lanza esta excepcion */
    public static void readChars(File f) throws FileNotFoundException, IOException {
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(f));
            int read = 0;
            while (read != -1) {
                read = fileIn.read();
                char c = (char) read;
            }
            fileIn.close();
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Lee el archivo por lineas *
     * @param f Un objeto File con el archivo que se desea leer
     * @throws java.io.FileNotFoundException En caso de que el archivo no exista
     * el metodo lanza esta excepcion
     * @throws java.io.IOException En caso de que el archivo no se pueda leer se
     * lanza esta excepcion */
    public static void readLines(File f) throws FileNotFoundException, IOException {
        try {
            BufferedReader fileIn = new BufferedReader(new FileReader(f));
            String line = "";
            while ((line = fileIn.readLine()) != null) {
            }
            fileIn.close();
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Guarda el String pasado como parametro a un archivo sobreescribiendo todo
     * lo que tenia antes
     *
     * @param f Un objeto File con el archivo al que se desea guardar
     * @param s El String a guardar
     * @throws java.io.IOException En caso de que el archivo no se pueda
     * escribir se lanza esta excepcion */
    public static void saveString(File f, String s) throws IOException {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(f));
            fileOut.println(s);
            fileOut.close();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Guarda el String pasado como parametro al final del archivo pasado como
     * parametro
     * @param f Un objeto File con el archivo al que se desea guardar
     * @param s El String a guardar
     * @throws java.io.IOException En caso de que el archivo no se pueda
     * escribir se lanza esta excepcion */
    public static void appendString(File f, String s) throws IOException {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(f, true));
            fileOut.println(s);
            fileOut.close();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
