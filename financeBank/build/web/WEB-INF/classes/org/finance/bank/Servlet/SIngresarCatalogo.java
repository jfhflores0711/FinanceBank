package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.TextUtil;

/**
 *
 * @author ZAMORA
 */
public class SIngresarCatalogo extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String myId = (String) session.getAttribute("USER_ID");
        String idForInitNewestTable = DateUtil.convertDateId(myId, SIngresarCatalogo.class.getSimpleName());
        String myIp = (String) session.getAttribute("USER_IP");
        try {
            String catalogo = request.getParameter("a");
            if (catalogo.equals("FILIAL")) {
                String codigo = nuevoCodigoFilial();
                String nombre = request.getParameter("b");
                if (nombre == null) {
                    nombre = "FILIAL" + codigo;
                }
                String direccion = request.getParameter("c");
                if (direccion == null) {
                    direccion = "N/A";
                }
                String estado = request.getParameter("d");
                if (estado == null) {
                    estado = "INACTIVO";
                }
                String telefono = request.getParameter("e");
                if (telefono == null) {
                    telefono = "S/n";
                }
                String distrito = request.getParameter("f");
                if (distrito == null) {
                    distrito = "";
                }
                String CodParaNumCuenta = codigo.substring(codigo.length() - 3);
                nombre = nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                direccion = direccion.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                TFilial filial = new TFilial();
                filial.setCodFilial(codigo);
                filial.setNombre(nombre);
                filial.setDireccion(direccion);
                filial.setEstado(estado);
                filial.setTelefono(TextUtil.trimMaxSize(telefono.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"), 10));
                filial.setIdUser(myId);
                filial.setIpUser(myIp);
                filial.setDateUser(DateUtil.getNOW_S());
                TDistrito dist = (TDistrito) dao.load(TDistrito.class, distrito);
                filial.setTDistrito(dist);
                filial.setCodParaNumCuenta(CodParaNumCuenta);
                dao.persist(filial);
                out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='SI'>");
                out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
            } else if (catalogo.equals("CAJA")) {
                String codfilial = request.getParameter("g");
                if (codfilial == null) {
                    codfilial = "0501";
                }
                String codigo = nuevoCodigoCaja(codfilial);
                String nombre = request.getParameter("b");
                if (nombre == null) {
                    nombre = "CAJA" + codigo;
                }
                String tipo = request.getParameter("h");
                if (tipo == null) {
                    tipo = "SECONDARY";
                }
                String estado = request.getParameter("d");
                if (estado == null) {
                    estado = "INACTIVO";
                }
                codigo = codigo.toUpperCase();
                nombre = nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                TCaja caja = new TCaja();
                caja.setCodCaja(codigo);
                caja.setNombreCaja(nombre);
                caja.setTipo(tipo);
                caja.setEstado(estado);
                caja.setIdUser(myId);
                caja.setIpUser(myIp);
                caja.setDateUser(DateUtil.getNOW_S());
                TFilial filial = (TFilial) dao.load(TFilial.class, codfilial);
                caja.setTFilial(filial);
                dao.persist(caja);
                Date fechaPenultima = new Date();
                List monedasList = dao.findAll("from TMoneda where estado='ACTIVO' order by codMoneda");
                if (monedasList.size() > 0) {
                    Iterator iteraMoneda = monedasList.iterator();
                    while (iteraMoneda.hasNext()) {
                        TMoneda moneda = (TMoneda) iteraMoneda.next();
                        TLogFinance logCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + caja.getCodCaja() + moneda.getCodMoneda());
                        List listSumas = dao.findAll("from Sumasnashot where idsuma like 'SUM" + caja.getCodCaja() + moneda.getCodMoneda() + "%'");
                        if (logCaja == null) {
                            TLogFinance l0g = new TLogFinance("LOG" + caja.getCodCaja() + moneda.getCodMoneda(), "SNAPSHOT", "",
                                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                                    "FECHA", ("LOG" + caja.getCodCaja() + moneda.getCodMoneda()).substring(3, 12), "ACTIVO", ("LOG" + caja.getCodCaja() + moneda.getCodMoneda()).substring(3, 7), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
                            dao.persist(l0g);
                            logCaja = l0g;
                        }
                        if (listSumas.isEmpty()) {
                            listSumas = createfirstSumasShot("SUM" + caja.getCodCaja() + moneda.getCodMoneda(), dao);
                        }
                        sacarHistorial(logCaja, listSumas, fechaPenultima, dao);
                    }
                }
                out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='SI'>");
                out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
            } else if (catalogo.equals("A")) {
                String txtN = request.getParameter("l");
                String txtD = request.getParameter("b");
                String txtM = request.getParameter("c");
                String txtI = request.getParameter("d");
                String txtA = request.getParameter("e");
                String txtB = request.getParameter("f");
                String txtG = request.getParameter("g");
                String txtE = request.getParameter("h");
                String txtH = request.getParameter("i");
                String txtJ = request.getParameter("j");
                String selI = request.getParameter("m");
                String selJ = request.getParameter("k");
                txtN = txtN.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                txtD = txtD.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                //public TTipoCredito(String idtipocredito, String nombre, String idUser, String ipUser, String dateUser,
                //BigDecimal montoMaximo, int tipoTasaInteres, BigDecimal tasaInteresCompensatorio)
                TTipoCredito ca = new TTipoCredito(idForInitNewestTable, txtN, myId, myIp, DateUtil.getNOW_S(),
                        new BigDecimal(txtM), 1, new BigDecimal(txtA));
                ca.setAvisoCuota(1);
                ca.setCobrarInteresCompensatorio(1);
                ca.setCobrarMora(1);
                ca.setCtaContableCapital("010");
                ca.setDescripcion(txtD);
                ca.setDiasEntreCuotas(Integer.parseInt(txtJ));
                ca.setFormaCalculoIntereses(Integer.parseInt(selI));
                ca.setFormaCobroIntereses(2);
                ca.setModoCalcIntComp(1);
                ca.setModoCalculoIntereses(1);
                ca.setModoCobroIntMora(1);
                ca.setMontoMinimo(new BigDecimal(txtE));
                ca.setNumMaxRefinanciacion(0);
                ca.setNumMaxReprogramacion(0);
                ca.setNumeroCuotasMax(Integer.parseInt(txtI));
                ca.setNumeroCuotasMin(Integer.parseInt(txtG));
                ca.setRedondeoCuota(0);
                ca.setRequiereGarantia(Integer.parseInt(selJ));
                ca.setTasaInteresMax(new BigDecimal(txtA));
                ca.setTasaInteresMin(new BigDecimal(txtH));
                ca.setTasaInteresMoratoria(new BigDecimal(txtB));
                ca.setTipoCuota(0);
                ca.setTipoTasaIntComp(1);
                ca.setTipoTasaIntMora(1);
                dao.persist(ca);
                out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='SI'>");
                out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
            } else if (catalogo.equals("PERSONA")) {
                String DNI = request.getParameter("i");
                if (DNI == null) {
                    DNI = "--";
                }
                String RUC = request.getParameter("j");
                if (RUC == null) {
                    RUC = "--";
                }
                String nombre = request.getParameter("b");
                if (nombre == null) {
                    nombre = "N/A";
                }
                String apellidos = request.getParameter("k");
                if (apellidos == null) {
                    apellidos = "--";
                }
                String email = request.getParameter("l");
                if (email == null) {
                    email = "--";
                }
                String ubigeo = request.getParameter("m");
                if (ubigeo == null) {
                    ubigeo = "--";
                }
                String telefono = request.getParameter("e");
                if (telefono == null) {
                    telefono = "--";
                }
                String celular = request.getParameter("n");
                if (celular == null) {
                    celular = "--";
                }
                String urlfoto = request.getParameter("o");
                if (urlfoto == null) {
                    urlfoto = "&nbsp;";
                }
                String urlfirma = request.getParameter("p");
                if (urlfirma == null) {
                    urlfirma = "&nbsp;";
                }
                String direccion = request.getParameter("c");
                if (direccion == null) {
                    direccion = "--";
                }
                String estado = request.getParameter("d");
                if (estado == null) {
                    estado = "ACTIVO";
                }
                String categoria = request.getParameter("q");
                if (categoria == null) {
                    categoria = "NATURAL";
                }
                nombre = nombre.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                apellidos = apellidos.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                email = email.toLowerCase();
                direccion = direccion.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
                TPersona persona = new TPersona();
                persona.setIdUserPk(idForInitNewestTable);
                persona.setDocIdentidad(DNI);
                persona.setRuc(RUC);
                persona.setNombre(nombre);
                persona.setApellidos(apellidos);
                persona.setEmail(email);
                persona.setUbigeo(ubigeo);
                persona.setTelefono(telefono.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setCelular(celular.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*"));
                persona.setDireccion(direccion);
                persona.setUrlFoto(urlfoto);
                persona.setUrlFirma(urlfirma);
                persona.setEstado(estado);
                TCategoriaPersona catper = (TCategoriaPersona) dao.load(TCategoriaPersona.class, categoria);
                persona.setTCategoriaPersona(catper);
                persona.setIdUser(myId);
                persona.setIpUser(myIp);
                persona.setDateUser(DateUtil.getNOW_S());
                dao.persist(persona);
                out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='SI'>");
                out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
            } else {
                out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
                out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
            }
        } catch (Exception ex) {
            out.println("<input type='text' id='txtRptaInsertar' name='txtRptaInsertar' value='NO'>");
            out.println("<input type='text' id='txtExisteCat' name='txtExisteCat' value='NO'>");
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String nuevoCodigoFilial() {
        DAOGeneral d = new DAOGeneral();
        List l = d.findAll("from TFilial order by codFilial DESC");
        int bv = l.size();
        if (bv > 0) {
            bv = Integer.parseInt(((TFilial) l.get(0)).getCodFilial());
            bv -= 500;
        }
        bv++;
        bv += 500;
        String s = String.valueOf(bv);
        if (s.length() < 4) {
            s = "0" + s;
        }
        return s;
    }

    private String nuevoCodigoCaja(String codFilial) {
        DAOGeneral d = new DAOGeneral();
        List l = d.findAll("from TCaja ca where ca.TFilial='" + codFilial + "' order by ca.codCaja DESC");
        int h = l.size();
        if (h > 0) {
            h = Integer.parseInt(((TCaja) l.get(0)).getCodCaja().replace(codFilial + "CA", ""));
        }
        h++;
        String s = String.valueOf(h);
        if (s.length() < 4) {
            s = "000".substring(s.length()) + s;
        }
        return codFilial + "CA" + s;
    }

    private static List createfirstSumasShot(String idSumas, DAOGeneral dao) {
        List sumasList = new ArrayList();
        TMoneda moneda = (TMoneda) dao.load(TMoneda.class, idSumas.substring(12, 15));
        Set listaDetalleDen = moneda.getTDenominacionMonedas();
        if (listaDetalleDen.size() > 0) {
            Iterator iteraDen = listaDetalleDen.iterator();
            while (iteraDen.hasNext()) {
                TDenominacionMoneda denActual = (TDenominacionMoneda) iteraDen.next();
                Sumasnashot suma = new Sumasnashot();
                suma.setCajero(idSumas.substring(3, 12));
                suma.setCantidad(0);
                suma.setEstado("ACTIVO" + denActual.getIddenominacionmoneda());
                String tamanio = String.valueOf(Double.parseDouble(denActual.getMonto()) * 100);
                suma.setIdsuma(idSumas + "000000000".substring(tamanio.length()) + tamanio);
                suma.setMoneda(moneda.getCodMoneda());
                suma.setMontodenominacio(denActual.getMonto());
                dao.persist(suma);
                sumasList.add(suma);
            }
        }
        return sumasList;
    }

    private static void sacarHistorial(TLogFinance logCaja, List listSumas, Date fecha, DAOGeneral dao) {
        try {
            String idBalance = DateUtil.getDate(fecha).replaceAll("/", "") + logCaja.getIdlogfinance().substring(3, 15);
            Logger.getLogger(SIngresarCatalogo.class.getName()).log(Level.INFO, "idBalance = " + idBalance);
            TBalancexmoneda balanceBackup = (TBalancexmoneda) dao.load(TBalancexmoneda.class, idBalance + "00");
            if (balanceBackup == null) {
                TMoneda moneda = (TMoneda) dao.load(TMoneda.class, logCaja.getIdlogfinance().substring(12, 15));
                balanceBackup = new TBalancexmoneda(idBalance + "00", moneda, logCaja.getActivoCajaybanco(),
                        logCaja.getActivoCuentaxcobrar(), logCaja.getPasivo(), logCaja.getEncaje(), logCaja.getPRespaldo(),
                        "BACKUP", "SYSTEM", "LOCAL", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha));
                balanceBackup.setFd(fecha);
                balanceBackup.setPatrimonio(logCaja.getPatrimonio());
                balanceBackup.setPatrimonioActual(logCaja.getPatrimonio());
                dao.persist(balanceBackup);
                Logger.getLogger(SIngresarCatalogo.class.getName()).log(Level.INFO, "moneda = " + moneda.getCodMoneda());
            }
            TDetalleCaja cajaBackup = (TDetalleCaja) dao.load(TDetalleCaja.class, idBalance + "00");
            if (cajaBackup == null) {
                cajaBackup = new TDetalleCaja(idBalance + "00", ((TCaja) dao.load(TCaja.class, logCaja.getIdlogfinance().substring(3, 12))), ((TMoneda) dao.load(TMoneda.class, logCaja.getIdlogfinance().substring(12, 15))),
                        DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha), "SYSTEM", "LOCAL", DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", fecha));
                cajaBackup.setMontoEntregado(logCaja.getMontoEntregado());
                cajaBackup.setMontoFinal(logCaja.getMontoFinal());
                cajaBackup.setMontoInicial(logCaja.getMontoInicial());
                cajaBackup.setMontoRecibido(logCaja.getMontoRecibido());
                cajaBackup.setEstado("ACTIVO");
                dao.persist(cajaBackup);
            }
            logCaja.setMontoInicial(logCaja.getMontoFinal());
            logCaja.setMontoEntregado(BigDecimal.ZERO);
            logCaja.setMontoRecibido(BigDecimal.ZERO);
            dao.update();
            /*TSumaMoneda sumaBackup = (TSumaMoneda) dao.load(TSumaMoneda.class, idBalance + "00");
            if (sumaBackup == null) {
                sumaBackup = new TSumaMoneda(idBalance + "00", "NOP");
                sumaBackup.setEstado("BACKUP");
                dao.persist(sumaBackup);
                Iterator it = listSumas.iterator();
                while (it.hasNext()) {
                    Sumasnashot suma = (Sumasnashot) it.next();
                    TDenominacionMoneda den = (TDenominacionMoneda) dao.load(TDenominacionMoneda.class, suma.getEstado().substring(6) + "");
                    String tamanio = String.valueOf(Double.parseDouble(den.getMonto()) * 100);
                    TDetalleSuma sumaBackupDetalle = (TDetalleSuma) dao.load(TDetalleSuma.class, idBalance + "000000000".substring(tamanio.length()) + tamanio + "");
                    if (sumaBackupDetalle == null) {
                        sumaBackupDetalle = new TDetalleSuma(idBalance + "000000000".substring(tamanio.length()) + tamanio + "", den, sumaBackup);
                        sumaBackupDetalle.setEstado(suma.getEstado());
                        sumaBackupDetalle.setCantidad(suma.getCantidad());
                        dao.persist(sumaBackupDetalle);
                        //Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "sumaBackupDetalle = " + sumaBackupDetalle.getIddetallesuma());
                    }
                    Logger.getLogger(adminCapital.class.getName()).log(Level.INFO, "den = " + den.getTMoneda().getNombre());
                }
            }*/
        } catch (Exception e) {
        }
    }
}
