package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.CurrencyConverter;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.PrestamoUtil;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author Admin
 */
public class SGenerarNuevaCuenta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String idPerCaja = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        String idForInitNewestTable = DateUtil.convertDateId(idPerCaja, SGenerarNuevaCuenta.class.getSimpleName());
        String idfechapersona;
        String DNI = request.getParameter("txtDNI");
        String myId = session.getAttribute("USER_ID").toString();
        String nombre = (request.getParameter("txtNombre"));
        String RUC = request.getParameter("txtRUC");
        String apellidos = (request.getParameter("txtApellidos"));
        String email = (request.getParameter("txtEmail"));
        String ubigeo = request.getParameter("txtUbigeo");
        String telefono = request.getParameter("txtTelefono");
        String celular = request.getParameter("txtCelular");
        String direccion = (request.getParameter("txtDireccion"));
        String idCategoriaPers = request.getParameter("selCategoriaPer");
        String existePer = (request.getParameter("txtPersonaExiste"));
        String stasa = request.getParameter("txtTasa");
        String tipomoneda = request.getParameter("lstMoneda");
        String idtipoCuenta = request.getParameter("selTipoCuenta");
        String txtMI = request.getParameter("txtMontoInicial");
        String txtOb = request.getParameter("observacion");
        String codFilial = (String) session.getAttribute("USER_CODFILIAL");
        String myIp = session.getAttribute("USER_IP").toString();
        String codCaja = session.getAttribute("USER_CODCAJA").toString();
        String txtDias = request.getParameter("txtNumDias");
        String txtDNIa = request.getParameter("txtDNIapoderado");
        String txtNA = request.getParameter("txtNombreApoderado");
        String txtAA = request.getParameter("txtApellidoApoderado");
        String txtFA = request.getParameter("txtFechaNacApoderado");
        String fnp = request.getParameter("txtTiempoPF");
        BigDecimal montoInicial = BigDecimal.ZERO;
        try {
            if (DNI == null) {
                DNI = "0";
            }
            if (RUC == null) {
                RUC = "0";
            }
            if (nombre == null) {
                nombre = "N/D";
            } else {
                nombre = nombre.toUpperCase();
            }
            if (apellidos == null) {
                apellidos = "&nbsp;";
            } else {
                apellidos = apellidos.toUpperCase();
            }
            if (email == null) {
                email = "&nbsp;";
            } else {
                email = email.toUpperCase();
            }
            if (ubigeo == null) {
                ubigeo = "&nbsp;";
            }
            if (telefono == null) {
                telefono = "&nbsp;";
            }
            if (celular == null) {
                celular = "&nbsp;";
            }
            if (direccion == null) {
                direccion = "&nbsp;";
            } else {
                direccion = direccion.toUpperCase();
            }
            if (idCategoriaPers == null) {
                request.getRequestDispatcher("nuevacuenta.htm?numcuent=null").forward(request, response);
            }
            if (txtMI == null || txtMI.equals("")) {
                txtMI = "0.00";
            } else {
                montoInicial = new BigDecimal(txtMI.replace(" ", "").replace(",", ""));
            }
            String urlfoto = "&nbsp;";
            String urlfirma = "&nbsp;";
            Double tasa;
            if (stasa != null) {
                tasa = Double.parseDouble(stasa.replace(" ", "").replace(",", ""));
            } else {
                tasa = 0.00;
            }
            if (existePer.equals("NO EXISTE")) {
                TPersona persona = new TPersona();
                persona.setIdUserPk(idForInitNewestTable);
                persona.setDocIdentidad(DNI);
                persona.setRuc(RUC);
                persona.setNombre(nombre);
                persona.setApellidos(apellidos);
                persona.setEmail(email);
                persona.setUbigeo(ubigeo);
                persona.setTelefono(telefono);
                persona.setCelular(celular);
                persona.setDireccion(direccion);
                persona.setUrlFoto(urlfoto);
                persona.setUrlFirma(urlfirma);
                persona.setEstado("ACTIVO");
                persona.setIdUser(myId);
                persona.setIpUser(myIp);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                persona.setDateUser(df.format(new Date()));
                TCategoriaPersona catPer = (TCategoriaPersona) dao.load(TCategoriaPersona.class, idCategoriaPers);
                persona.setTCategoriaPersona(catPer);
                dao.persist(persona);
                String tipoPers = (String) dao.findAll("select tipoPer.idtipopersona from TTipoPersona tipoPer where tipoPer.descripcion ='USUARIO'").get(0);
                TTipoPersona tipoPer = (TTipoPersona) dao.load(TTipoPersona.class, tipoPers);
                TControlTipo ControlTipo = new TControlTipo();
                ControlTipo.setIdcontroltipo(idForInitNewestTable);
                ControlTipo.setTPersona(persona);
                ControlTipo.setTTipoPersona(tipoPer);
                ControlTipo.setIdUser(myId);
                ControlTipo.setIpUser(myIp);
                df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                ControlTipo.setDateUser(df.format(new Date()));
                ControlTipo.setEstado("ACTIVO");
                dao.persist(ControlTipo);
                idfechapersona = idForInitNewestTable;
            } else {
                idfechapersona = request.getParameter("txtdatapersona");
            }
            TCuentaPersona cuentapersona = new TCuentaPersona();
            cuentapersona.setIdcuentapersona(idForInitNewestTable);
            String numCuenta = "";
            String monedas = (String) dao.findAll("select money.codParaNumCuenta from TMoneda money where money.codMoneda ='" + tipomoneda + "'").get(0);
            String tipCuent = (String) dao.findAll("select tipcuen.codParaNumCuenta from TTipoCuenta tipcuen where tipcuen.codigoCuenta ='" + idtipoCuenta + "'").get(0);
            boolean ok = false;
            while (!ok) {
                numCuenta = nuevaCuenta(codFilial, monedas, tipCuent);
                List l = dao.findAll("select t.numCta from TCuentaPersona t where t.numCta ='" + numCuenta + "'");
                if (l.isEmpty() || l.size() < 1) {
                    ok = true;
                } else {
                    ok = false;
                }
            }
            cuentapersona.setNumCta(numCuenta);
            cuentapersona.setSaldo(montoInicial);
            cuentapersona.setSaldoSinInteres(montoInicial);
            cuentapersona.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
            cuentapersona.setEstado("ACTIVO");
            if (!(txtOb.equals(""))) {
                cuentapersona.setObservaciones(txtOb.toUpperCase());
            } else {
                cuentapersona.setObservaciones("&nbsp;");
            }
            cuentapersona.setIdUser(myId);
            cuentapersona.setIpUser(myIp);
            cuentapersona.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
            cuentapersona.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
            cuentapersona.setFechaCap(DateUtil.getDateTime("yyyy/MM/dd", new Date()));
            TMoneda moneda = (TMoneda) dao.load(TMoneda.class, tipomoneda);
            cuentapersona.setTMoneda(moneda);
            TPersona pers = (TPersona) dao.load(TPersona.class, idfechapersona);
            cuentapersona.setTPersona(pers);
            TTipoCuenta tipocuenta = (TTipoCuenta) dao.load(TTipoCuenta.class, idtipoCuenta);
            cuentapersona.setTTipoCuenta(tipocuenta);
            cuentapersona.setInteres(new BigDecimal(tasa));
            dao.persist(cuentapersona);
            if (idtipoCuenta.equals("CPF")) {
                TDetalleCuentaPersona detaCuenPer = new TDetalleCuentaPersona();
                detaCuenPer.setIddetallecuentapersona(idForInitNewestTable);
                detaCuenPer.setNumDias(txtDias);
                if (fnp == null) {
                    fnp = Double.toString(DateUtil.today() + 30);
                } else {
                    fnp = fnp.trim();
                    String[] fh = fnp.split("/");
                    fnp = Double.toString(DateUtil.date(Double.parseDouble(fh[2]), Double.parseDouble(fh[1]), Double.parseDouble(fh[0])));
                }
                detaCuenPer.setFechaPlazo(fnp);
                detaCuenPer.setInteres(new BigDecimal(tasa));
                detaCuenPer.setIdUser(myId);
                detaCuenPer.setIpUser(myIp);
                DateFormat df3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                detaCuenPer.setDateUser(df3.format(new Date()));
                detaCuenPer.setTCuentaPersona(cuentapersona);
                dao.persist(detaCuenPer);
            }
            TOperacion operacion = new TOperacion();
            operacion.setIdOperacion(idForInitNewestTable);
            TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, idPerCaja);
            operacion.setNumeroOperacion(numeroOperacion.getNumber(codFilial, codCaja));
            operacion.setDescripcion("APERTURA DE CUENTA con monto = " + txtMI);
            DateFormat df4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            operacion.setFecha(df4.format(new Date()));
            operacion.setEstado("ACTIVO");
            operacion.setIdUser(myId);
            operacion.setIpUser(myIp);
            operacion.setDateUser(df4.format(new Date()));
            TTipoOperacion tipoOperacion = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC3");
            operacion.setTTipoOperacion(tipoOperacion);
            TMoneda mimoneda = (TMoneda) dao.load(TMoneda.class, tipomoneda);
            operacion.setTMoneda(mimoneda);
            operacion.setSaldofinal(montoInicial);
            operacion.setSaldoFinalSinInteres(montoInicial);
            operacion.setTPersonaCaja(pcaja);
            operacion.setFd(new Date());
            dao.persist(operacion);
            TRegistroDepositoRetiro RegistroDepRet = new TRegistroDepositoRetiro();
            RegistroDepRet.setIddepositoretiro(idForInitNewestTable);
            df4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            RegistroDepRet.setFecha(df4.format(new Date()));
            RegistroDepRet.setNumCta(numCuenta);
            RegistroDepRet.setImporte(montoInicial);
            RegistroDepRet.setDniRepresentante(txtDNIa);
            RegistroDepRet.setNombreRepresentante(txtNA);
            RegistroDepRet.setApellidosRepresentante(txtAA);
            RegistroDepRet.setFechaNacRepresentante(txtFA);
            RegistroDepRet.setIdUser(myId);
            RegistroDepRet.setIpUser((String) myIp);
            RegistroDepRet.setDateUser(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
            RegistroDepRet.setTCuentaPersona(cuentapersona);
            RegistroDepRet.setTOperacion(operacion);
            RegistroDepRet.setEstado("ACTIVO");
            dao.persist(RegistroDepRet);
            if (!montoInicial.equals(BigDecimal.ZERO)) {
//                String hql = iniDetalleCaja.detalleActivaCaja(codCaja, tipomoneda);
                //TDetalleCaja detaCaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + codCaja + mimoneda.getCodMoneda() + "00");
                TLogFinance detaCaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + codCaja + mimoneda.getCodMoneda());
                detaCaja.setMontoFinal(detaCaja.getMontoFinal().add(montoInicial));
                detaCaja.setMontoRecibido(detaCaja.getMontoRecibido().add(montoInicial));
                //detaCaja.setDateUser(DateUtil.getNOW_S());
                dao.update();
            }
            String numCertificado = "";
            if (idtipoCuenta.equals("CPF")) {
                TCertificado tcertificado = new TCertificado();
                tcertificado.setIdcertificado(idForInitNewestTable);
                tcertificado.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                numCertificado = PrestamoUtil.nextNumberCertificado(codFilial);
                tcertificado.setNumCertificado(numCertificado);
                tcertificado.setDniRuc(cuentapersona.getTPersona().getDocIdentidad());
                tcertificado.setNombre(cuentapersona.getTPersona().getNombre());
                tcertificado.setNombreRep(cuentapersona.getTPersona().getApellidos());
                tcertificado.setDescripcion(new String("nonurl"));
                tcertificado.setTipo("CERTIFICADO");
                tcertificado.setTRegistroDepositoRetiro(RegistroDepRet);
                dao.persist(tcertificado);
                TCertificado tcertificadoAnexo = new TCertificado();
                tcertificadoAnexo.setIdcertificado(DateUtil.convertDateId(idPerCaja, SGenerarNuevaCuenta.class.getSimpleName()));
                tcertificadoAnexo.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                tcertificadoAnexo.setNumCertificado("0");
                tcertificadoAnexo.setDniRuc(cuentapersona.getTPersona().getDocIdentidad());
                tcertificadoAnexo.setNombre(cuentapersona.getTPersona().getNombre());
                tcertificadoAnexo.setNombreRep(cuentapersona.getTPersona().getApellidos());
                tcertificadoAnexo.setDescripcion(new String("nonurl"));
                tcertificadoAnexo.setTipo("ANEXO");
                tcertificadoAnexo.setTRegistroDepositoRetiro(RegistroDepRet);
                dao.persist(tcertificadoAnexo);
            }
            TRegistroDepositoRetiro regDepRet = (TRegistroDepositoRetiro) dao.findAll("from TRegistroDepositoRetiro where iddepositoretiro='" + idForInitNewestTable + "'").get(0);
            Map ticket = new HashMap();
            ticket.put("RUC", regDepRet.getTCuentaPersona().getTPersona().getRuc());
            ticket.put("DNI", regDepRet.getTCuentaPersona().getTPersona().getDocIdentidad());
            ticket.put("TIPOPERSONA", regDepRet.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
            ticket.put("TIPOOPERACION", regDepRet.getTOperacion().getTTipoOperacion().getNombre());
            ticket.put("IDOPERACION", regDepRet.getTOperacion().getIdOperacion());
            ticket.put("NUMEROOPERACION", regDepRet.getTOperacion().getNumeroOperacion());
            ticket.put("CODIGOCAJA", regDepRet.getTOperacion().getTPersonaCaja().getTCaja().getCodCaja());
            String Filial = regDepRet.getTOperacion().getTPersonaCaja().getTCaja().getTFilial().getNombre();
            Filial = Filial.replace("FILIAL", "OFICINA");
            ticket.put("FILIAL", Filial);
            ticket.put("MONEDA", regDepRet.getTCuentaPersona().getTMoneda().getNombre());
            String verfecha = regDepRet.getFecha().substring(0, 10);
            String[] fechapfx;
            fechapfx = verfecha.split("/");
            String cad2x = fechapfx[2] + "/" + fechapfx[1] + "/" + fechapfx[0];
            String verhora = regDepRet.getFecha().substring(11);
            ticket.put("FECHA", cad2x);
            ticket.put("HORA", verhora);
            ticket.put("NOMBRE", regDepRet.getTCuentaPersona().getTPersona().getNombre());
            ticket.put("APELLIDOS", regDepRet.getTCuentaPersona().getTPersona().getApellidos());
            ticket.put("NOMBREREPRESENTANTE", regDepRet.getNombreRepresentante());
            ticket.put("APELLIDOSPRESENTANTE", regDepRet.getApellidosRepresentante());
            String c = regDepRet.getTCuentaPersona().getNumCta();
            ticket.put("NUMEROCUENTA", c.substring(0, 5) + "-" + c.substring(5, 6) + "-" + c.substring(6));
            ticket.put("CODTIPOCUENTA", regDepRet.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta());
            ticket.put("TIPOCUENTA", regDepRet.getTCuentaPersona().getTTipoCuenta().getDescripcion());
            ticket.put("MON", regDepRet.getTCuentaPersona().getTMoneda().getSimbolo());
            ticket.put("MONTO", CurrencyConverter.formatToMoneyUS(regDepRet.getImporte().doubleValue(), 2));
            ticket.put("IMPORTE", CurrencyConverter.formatToMoneyUS(regDepRet.getImporte().doubleValue(), 2));
            ticket.put("CATEGORIAPERSONA", regDepRet.getTCuentaPersona().getTPersona().getTCategoriaPersona().getDescripcion());
            ticket.put("IDCERTIFICADO", idForInitNewestTable);
            ticket.put("NUMEROCERTIFICADO", numCertificado);
            ticket.put("IDANEXO", idForInitNewestTable);
            if (regDepRet.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                TDetalleCuentaPersona detalleCuentPers = (TDetalleCuentaPersona) dao.findAll("from TDetalleCuentaPersona detcuenper where detcuenper.TCuentaPersona='" + regDepRet.getTCuentaPersona().getIdcuentapersona() + "'").get(0);
                ticket.put("INTERES", detalleCuentPers.getInteres());
                ticket.put("NUMERODIASPF", detalleCuentPers.getNumDias());
                String cad = PrestamoUtil.eedatefmt(PrestamoUtil.fmtdate9, Double.parseDouble(detalleCuentPers.getFechaPlazo())).replace(",", "");
                String[] fechapf;
                fechapf = cad.split("/");
                String cad2 = fechapf[2] + "/" + fechapf[1] + "/" + fechapf[0];
                ticket.put("FECHAPF", cad2);
            } else {
                ticket.put("INTERES", regDepRet.getTCuentaPersona().getInteres());
            }
            ticket.put("SALDO", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue(), 2));
            ticket.put("SALDO1", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
            ticket.put("INT", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue() - regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
            session.setAttribute("ticket", ticket);
            session.setAttribute("myId_PK", idfechapersona);
            session.setAttribute("id_user_PK", idfechapersona);
            request.getRequestDispatcher("nuevacuenta.htm?numcuent=" + numCuenta).forward(request, response);
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

    private String nuevaCuenta(String codFilial, String monedas, String tipCuent) {
        String numCuenta = "51";
        if (codFilial.length() > 3) {
            codFilial = codFilial.substring(codFilial.length() - 3);
        }
        numCuenta += codFilial;
        numCuenta += monedas;
        numCuenta += tipCuent;
        String now = Integer.toString((int) (Math.random() * 1000000));
        String codigox = "000000".substring(now.length()) + now;
        numCuenta += codigox;
        Integer numeros = 0;
        for (int l = 0; l < numCuenta.length(); l++) {
            numeros += Integer.parseInt(numCuenta.substring(l, l + 1));
        }
        int check = ((numeros % 10) == 0 ? 0 : (10 - (numeros % 10))); //resta 10 - residuo y si residuo es 0 no resta
        numCuenta += Integer.toString(check); //codigo de verificacion
        return numCuenta;
    }
}
