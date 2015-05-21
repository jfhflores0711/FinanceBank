package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.finance.bank.bean.*;
import org.finance.bank.util.*;
import org.finance.bank.model.dao.DAOGeneral;

/**
 *
 * @author roger
 */
public class SRegistrarDepositoRetiro extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        DAOGeneral dao = new DAOGeneral();
        String id_user = (String) session.getAttribute("USER_ID");
        String ip_user = (String) session.getAttribute("USER_IP");
        String idForInitNewestTable = DateUtil.convertDateId(id_user, SRegistrarDepositoRetiro.class.getSimpleName());
        String idForInitNewestTable2 = DateUtil.convertDateId(id_user, SRegistrarDepositoRetiro.class.getSimpleName());
        String codM = request.getParameter("d");
        String codCaja = (String) session.getAttribute("USER_CODCAJA");
        String codFilial = session.getAttribute("USER_CODFILIAL").toString();
        String pcajaId = session.getAttribute("USER_ID_PERSONA_CAJA").toString();
        try {
            String idCuenta = request.getParameter("i");
            String tipoTransaccion = request.getParameter("a");
            String numCuenta = request.getParameter("b");
            String Knegativo = request.getParameter("k");
            String PCobroPF = request.getParameter("l");
            String xMActual = (String) session.getAttribute("xM" + request.getParameter("d").toString());
            String monedaCaja = NumberUtil.converseS2N(xMActual);
            if (monedaCaja.equals("")) {
                monedaCaja = "0.00";
            }
            Double montoenCaja = Double.parseDouble(monedaCaja);
            session.setAttribute("knegativo", "NO");
            List result1 = dao.findAll("from TCuentaPersona where idcuentapersona='" + idCuenta + "'");
            int cuentas = result1.size();
            if (cuentas != 1) {
                out.print("ERROR: no se puede realizar ninguna transaccion");
                out.close();
                return;
            }
            TCuentaPersona cu = (TCuentaPersona) result1.get(0);
            String importeString = request.getParameter("e");
            if (importeString == null || cu == null) {
                out.print("Error al procesar la transaccion NO SE HACE NADA");
                out.close();
                return;
            }
            importeString = importeString.replace(",", "").replace(" ", "");
            BigDecimal importe = new BigDecimal(importeString);
            Double doubimporte = importe.doubleValue();
            if (montoenCaja >= doubimporte || tipoTransaccion.equals("DEPOSITO")) {
                if (cu.getSaldoSinInteres().doubleValue() >= doubimporte || tipoTransaccion.equals("DEPOSITO") || Knegativo.equals("SI")) {
                    BigDecimal b = BigDecimal.ZERO;
                    TCuentaPersona cupe = cu;
                    String f = "";
                    String fechapf = "";
                    String fec = "";
                    if (cupe.getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                        String hql = "from TDetalleCuentaPersona deta where deta.TCuentaPersona='" + cupe.getIdcuentapersona() + "'";
                        TDetalleCuentaPersona detacupe = (TDetalleCuentaPersona) dao.findAll(hql).get(0);
                        f = Double.toString(DateUtil.today());
                        fechapf = detacupe.getFechaPlazo();
                        fec = PrestamoUtil.eedatefmt(PrestamoUtil.fmtdate9, Double.parseDouble(fechapf));
                    }
                    if ((cupe.getTTipoCuenta().getCodigoCuenta().equals("CPF") && tipoTransaccion.equals("RETIRO") && (Double.parseDouble(fechapf) < Double.parseDouble(f) || PCobroPF.equals("2649232134")))
                            || (cupe.getTTipoCuenta().getCodigoCuenta().equals("CPF") && tipoTransaccion.equals("DEPOSITO"))
                            || (cupe.getTTipoCuenta().getCodigoCuenta().equals("CCO"))
                            || (cupe.getTTipoCuenta().getCodigoCuenta().equals("CAH"))) {
                        TTipoOperacion tipoOperacion;
                        if (tipoTransaccion.equals("DEPOSITO")) {
                            tipoOperacion = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC3");
                        } else {
                            tipoOperacion = (TTipoOperacion) dao.load(TTipoOperacion.class, "TIPC4");
                        }
                        TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, pcajaId);
                        DateFormat df4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        TMoneda mimoneda = (TMoneda) dao.load(TMoneda.class, codM);
                        TOperacion operacion = new TOperacion(idForInitNewestTable, tipoOperacion, pcaja, mimoneda,
                                numeroOperacion.getNumber(codFilial, codCaja), df4.format(new Date()), "ACTIVO", id_user, ip_user, df4.format(new Date()));
                        if (tipoTransaccion.equals("DEPOSITO")) {
                            operacion.setDescripcion(DateUtil.getDate(new Date()) + " DEPOSITO");
                        } else {
                            operacion.setDescripcion(DateUtil.getDate(new Date()) + " RETIRO");
                        }
                        operacion.setFd(new Date());
                        dao.persist(operacion);
                        TRegistroDepositoRetiro RegistroDepRet = new TRegistroDepositoRetiro(idForInitNewestTable, cupe, operacion,
                                df4.format(new Date()), numCuenta, importe, "ACTIVO", id_user, ip_user, df4.format(new Date()));
                        session.setAttribute("idDepositoRetiro", idForInitNewestTable);
                        String nombreDepositante = request.getParameter("g");
                        RegistroDepRet.setNombreRepresentante(nombreDepositante.toUpperCase().replaceAll(";", "&"));
                        String apellidosDepositante = request.getParameter("h");
                        RegistroDepRet.setApellidosRepresentante(apellidosDepositante.toUpperCase().replaceAll(";", "&"));
                        RegistroDepRet.setNumeroCheque(request.getParameter("m"));
                        dao.persist(RegistroDepRet);
                        Double saldoSinInteresAntes = 0D;
                        boolean sobregiro = false;
                        TCuentaPersona cuenPer = (TCuentaPersona) dao.load(TCuentaPersona.class, idCuenta);
                        if (Knegativo.equals("SI") && tipoTransaccion.equals("RETIRO") && cuenPer.getSaldoSinInteres().doubleValue() < doubimporte) {
                            Double saldo = (cuenPer.getSaldo().doubleValue() >= 0 ? cuenPer.getSaldo().doubleValue() : 0D);
                            saldoSinInteresAntes = (cuenPer.getSaldoSinInteres().doubleValue() >= 0 ? cuenPer.getSaldoSinInteres().doubleValue() : 0D);
                            sobregiro = true;
                            Double prestamo = doubimporte - saldo;
                            b = new BigDecimal(doubimporte - saldoSinInteresAntes);
                            session.setAttribute("knegativo", "SI");
                            TSobregiro sobreGiro = new TSobregiro(idForInitNewestTable, cuenPer, id_user, ip_user, DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                            sobreGiro.setMontoActual(new BigDecimal(prestamo));
                            sobreGiro.setMontoSinInteres(b);
                            sobreGiro.setInteressg(new BigDecimal((Double.parseDouble(request.getParameter("o")))));
                            sobreGiro.setFechaPago(request.getParameter("p"));
                            sobreGiro.setEstado("ACTIVO");
                            sobreGiro.setFechaActualizacion(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                            sobreGiro.setFechaCap(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                            sobreGiro.setIdRegistro(RegistroDepRet.getIddepositoretiro());
                            dao.persist(sobreGiro);
                        }
                        cuenPer.setObservaciones(request.getParameter("j"));
                        if (tipoTransaccion.equals("DEPOSITO")) {
                            cuenPer.setSaldo(cuenPer.getSaldo().add(importe));
                            cuenPer.setSaldoSinInteres(cuenPer.getSaldoSinInteres().add(importe));
                            cuenPer.setFechaActualizacion(DateUtil.getNOW_S());
                            dao.update();
                            if (cuenPer.getSaldoSinInteres().doubleValue() > importe.doubleValue()) {
                                cuenPer.setEstado("ACTIVO");
                                dao.update();
                            } else {
                                List l2 = dao.findAll("from TSobregiro where estado='ACTIVO' and TCuentaPersona.idcuentapersona='" + idCuenta + "' order by dateUser");
                                int s = l2.size();
                                for (int i = 0; i < s; i++) {
                                    TSobregiro s1 = (TSobregiro) l2.get(i);
                                    if (s1.getMontoSinInteres().doubleValue() > doubimporte) {
                                        s1.setMontoSinInteres(s1.getMontoSinInteres().subtract(new BigDecimal(doubimporte)));
                                        s1.setMontoActual(s1.getMontoActual().subtract(new BigDecimal(doubimporte)));
                                        dao.update();
                                        b = b.add(new BigDecimal(doubimporte));
                                        doubimporte = 0D;
                                        break;
                                    } else {
                                        b = b.add(s1.getMontoSinInteres());
                                        doubimporte = doubimporte - s1.getMontoSinInteres().doubleValue();
                                        s1.setMontoActual(s1.getMontoActual().subtract(s1.getMontoSinInteres()));
                                        s1.setMontoSinInteres(BigDecimal.ZERO);
                                        s1.setEstado("CANCELADO");
                                        dao.update();
                                        cuenPer.setEstado("ACTIVO");
                                        dao.update();
                                    }
                                }
                            }
                            System.out.println("b = " + b);
                        } else {
                            cuenPer.setSaldo(cuenPer.getSaldo().subtract(importe));
                            cuenPer.setSaldoSinInteres(cuenPer.getSaldoSinInteres().subtract(importe));
                            dao.update();
                            if (sobregiro == true) {
                                cuenPer.setEstado("SOBREGIRO");
                            } else if (cuenPer.getSaldoSinInteres().compareTo(BigDecimal.ZERO) == 0) {
                                cuenPer.setEstado("ACTIVO");
                            }
                            dao.update();
                        }
                        TOperacion operacion2 = (TOperacion) dao.load(TOperacion.class, idForInitNewestTable);
                        operacion2.setSaldoFinalSinInteres(cuenPer.getSaldoSinInteres());
                        operacion2.setSaldofinal(cuenPer.getSaldo());
                        dao.update();
                        String numCertificado = "";
                        if (tipoTransaccion.equals("DEPOSITO") && cupe.getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                            TCertificado tcertificado = new TCertificado();
                            tcertificado.setIdcertificado(idForInitNewestTable);
                            tcertificado.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                            numCertificado = PrestamoUtil.nextNumberCertificado(codFilial);
                            tcertificado.setNumCertificado(numCertificado);
                            tcertificado.setDniRuc(cupe.getTPersona().getDocIdentidad());
                            tcertificado.setNombre(cupe.getTPersona().getNombre());
                            tcertificado.setNombreRep(cupe.getTPersona().getApellidos());
                            tcertificado.setDescripcion(new String("nonurl"));
                            tcertificado.setTipo("CERTIFICADO");
                            tcertificado.setTRegistroDepositoRetiro(RegistroDepRet);
                            dao.persist(tcertificado);
                            TCertificado tcertificadoAnexo = new TCertificado();
                            tcertificadoAnexo.setIdcertificado(idForInitNewestTable2);
                            tcertificadoAnexo.setFecha(DateUtil.getDateTime("yyyy/MM/dd HH:mm:ss", new Date()));
                            tcertificadoAnexo.setNumCertificado(" ");
                            tcertificadoAnexo.setDniRuc(cupe.getTPersona().getDocIdentidad());
                            tcertificadoAnexo.setNombre(cupe.getTPersona().getNombre());
                            tcertificadoAnexo.setNombreRep(cupe.getTPersona().getApellidos());
                            tcertificadoAnexo.setDescripcion(new String("nonurl"));
                            tcertificadoAnexo.setTipo("ANEXO");
                            tcertificadoAnexo.setTRegistroDepositoRetiro(RegistroDepRet);
                            dao.persist(tcertificadoAnexo);
                        }
                        TLogFinance log = (TLogFinance) dao.load(TLogFinance.class, "LOG" + codCaja + mimoneda.getCodMoneda());
                        if (tipoTransaccion.equals("DEPOSITO")) {
                            log.setMontoFinal(log.getMontoFinal().add(importe));
                            log.setMontoRecibido(log.getMontoRecibido().add(importe));
                            log.setActivoCajaybanco(log.getActivoCajaybanco().add(importe));
                            log.setActivoCuentaxcobrar(log.getActivoCuentaxcobrar().subtract(b));
                            log.setMonto(log.getMonto().add(b));
                            log.setPasivo(log.getPasivo().add(importe.subtract(b)));
                            log.setEncaje(log.getEncaje().subtract(b));
                        } else {
                            log.setMontoFinal(log.getMontoFinal().subtract(importe));
                            log.setMontoEntregado(log.getMontoEntregado().add(importe));
                            log.setActivoCajaybanco(log.getActivoCajaybanco().subtract(importe));
                            log.setActivoCuentaxcobrar(log.getActivoCuentaxcobrar().add(b));
                            log.setMonto(log.getMonto().subtract(b));
                            log.setPasivo(log.getPasivo().subtract(importe.subtract(b)));
                            log.setEncaje(log.getEncaje().add(b));
                        }
                        dao.update();
//                        BeanUtil bu = new BeanUtil(log);
//                        log(bu.toString());
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
                        ticket.put("FECHA", operacion.getFecha().substring(8, 10) + "/" + operacion.getFecha().substring(5, 8) + operacion.getFecha().substring(0, 4));
                        ticket.put("HORA", operacion.getFecha().substring(11));
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
                        ticket.put("IDANEXO", idForInitNewestTable2);
                        if (regDepRet.getTCuentaPersona().getTTipoCuenta().getCodigoCuenta().equals("CPF")) {
                            TDetalleCuentaPersona detalleCuentPers = (TDetalleCuentaPersona) dao.findAll("from TDetalleCuentaPersona detcuenper where detcuenper.TCuentaPersona='" + regDepRet.getTCuentaPersona().getIdcuentapersona() + "'").get(0);
                            ticket.put("INTERES", detalleCuentPers.getInteres());
                            ticket.put("NUMERODIASPF", detalleCuentPers.getNumDias());
                            ticket.put("FECHAPF", DateUtil.eedatefmt(DateUtil.fmtdate24, Double.parseDouble(detalleCuentPers.getFechaPlazo())).replace(",", ""));
                        } else {
                            ticket.put("INTERES", regDepRet.getTCuentaPersona().getInteres());
                        }
                        ticket.put("SALDO", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue(), 2));
                        ticket.put("SALDO1", CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        ticket.put("INT",CurrencyConverter.formatToMoneyUS(regDepRet.getTOperacion().getSaldofinal().doubleValue()-regDepRet.getTOperacion().getSaldoFinalSinInteres().doubleValue(), 2));
                        session.setAttribute("ticket", ticket);
                        out.println("<input id='txtRespReg' name='txtRespReg' type='text' style='display:none' value='OK'>");
                        out.println("<input id='txtExisteMontoCaja' name='txtExisteMontoCaja' type='text' style='display:none' value='OK'>");
                        out.println("<input id='txtVanPF' name='txtVanPF' type='text' style='display:none' value=''>");
                        out.print("<input id='txtCobroPF' name='txtCobroPF' type='text' style='display:none' value='x'>");
                    } else {
                        out.println("<input id='txtRespReg' name='txtRespReg' type='text' style='display:none' value='OK'>");
                        out.println("<input id='txtVanPF' name='txtVanPF' type='text' style='display:none' value='" + fec.replace(",", "") + "'>");
                        out.println("<input id='txtExisteMontoCaja' name='txtExisteMontoCaja' type='text' style='display:none' value='OK'>");
                        out.print("<input id='txtCobroPF' name='txtCobroPF' type='text' style='display:none' value='x'>");
                    }
                } else {
                    out.println("<input id='txtRespReg' name='txtRespReg' type='text' style='display:none' value='NO HAY DINERO SUFICIENTE'>");
                    out.println("<input id='txtExisteMontoCaja' name='txtExisteMontoCaja' type='text' style='display:none' value='OK'>");
                    out.println("<input id='txtVanPF' name='txtVanPF' type='text' style='display:none' value=''>");
                    out.print("<input id='txtCobroPF' name='txtCobroPF' type='text' style='display:none' value='x'>");
                }
            } else {
                out.println("<input id='txtRespReg' name='txtRespReg' type='text' style='display:none' value=''>");
                out.println("<input id='txtExisteMontoCaja' name='txtExisteMontoCaja' type='text' style='display:none' value='NO HAY DINERO EN CAJA'>");
                out.println("<input id='txtVanPF' name='txtVanPF' type='text' style='display:none' value=''>");
                out.print("<input id='txtCobroPF' name='txtCobroPF' type='text' style='display:none' value='x'>");
            }
        } finally {
            out.close();//dao.cerrarSession();
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
}
