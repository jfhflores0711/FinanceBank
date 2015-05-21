package org.finance.bank.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.finance.bank.bean.TLogFinance;
import org.finance.bank.bean.TMoneda;
import org.finance.bank.bean.TOperacion;
import org.finance.bank.bean.TPersonaCaja;
import org.finance.bank.bean.TRegistroCompraVenta;
import org.finance.bank.bean.TSumaMoneda;
import org.finance.bank.bean.TTipoCambio;
import org.finance.bank.bean.TTipoOperacion;
import org.finance.bank.model.dao.DAOGeneral;
import org.finance.bank.util.DateUtil;
import org.finance.bank.util.formatMoneda;
import org.finance.bank.util.numeroOperacion;

/**
 *
 * @author Administrador
 */
public class SRegistrarCambio extends HttpServlet {

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
        String myId = (String) session.getAttribute("USER_ID");
        String idForInitNewestTable = DateUtil.convertDateId(myId, SRegistrarCambio.class.getSimpleName());
        String myIp = (String) session.getAttribute("USER_IP");
        String myIdTc = (String) session.getAttribute("ID_TIPOCAMBIO");
        String myIdPc = (String) session.getAttribute("USER_ID_PERSONA_CAJA");
        String myCodF = (String) session.getAttribute("USER_CODFILIAL");
        String myCodC = (String) session.getAttribute("USER_CODCAJA");
        try {
            String id_tipocambio = "";
            if (myIdTc != null && !myIdTc.equals("")) {
                id_tipocambio = myIdTc;
            } else {
                out.print("Error de session, no se hace nada");
                out.close();
                dao.cerrarSession();
                return;
            }
            BigDecimal recibido = BigDecimal.ZERO;
            if (request.getParameter("recibido") != null && !request.getParameter("recibido").equals("")) {
                recibido = new BigDecimal(request.getParameter("recibido").toString());
            } else {
                out.print("No hacer nada");
                out.close();
                dao.cerrarSession();
                return;
            }
            String razonSocial = "";
            if (request.getParameter("razonSocial") != null && !request.getParameter("razonSocial").equals("")) {
                razonSocial = request.getParameter("razonSocial").toString();
                razonSocial= razonSocial.replace("SHRP", "#").replaceAll("AMPSD", "&").replaceAll("ASTRX", "*").toUpperCase();
            }
            String cod_moneda = "";
            if (request.getParameter("cod_moneda") != null && !request.getParameter("cod_moneda").equals("")) {
                cod_moneda = request.getParameter("cod_moneda").toString();
            } else {
                out.print("No se puede operar");
                out.close();
                dao.cerrarSession();
                return;
            }
            String tasa = "";
            if (request.getParameter("tasa") != null && !request.getParameter("tasa").equals("")) {
                tasa = request.getParameter("tasa").toString();
            } else {
                out.print("No se puede calcular");
                out.close();
                dao.cerrarSession();
                return;
            }
            BigDecimal entregado = BigDecimal.ZERO;
            if (request.getParameter("entregado") != null && !request.getParameter("entregado").equals("")) {
                entregado = new BigDecimal(request.getParameter("entregado").toString());
            } else {
                out.print("Problema de parámetros");
                out.close();
                dao.cerrarSession();
                return;
            }
            String tipo = "";
            if (request.getParameter("tipo") != null && !request.getParameter("tipo").equals("")) {
                tipo = request.getParameter("tipo").toString();
            } else {
                out.print("No se obtiene ningun resultado");
                out.close();
                dao.cerrarSession();
                return;
            }
            String ddeterioro = "";
            if (request.getParameter("ddeterioro") != null && !request.getParameter("ddeterioro").equals("")) {
                ddeterioro = request.getParameter("ddeterioro").toString();
            }
            String codTipo = (tipo.equals("COMPRA")) ? "TIPC1" : "TIPC2";
            TTipoCambio tipocambio = (TTipoCambio) dao.load(TTipoCambio.class, id_tipocambio);
            TPersonaCaja pcaja = (TPersonaCaja) dao.load(TPersonaCaja.class, myIdPc);
            TOperacion operacion = new TOperacion();
            operacion.setIdOperacion(idForInitNewestTable);
            operacion.setDescripcion(tipo + "* CAMBIO DE MONEDA**" + razonSocial);
            operacion.setFecha(DateUtil.getNOW_S());
            TTipoOperacion tipoOper = (TTipoOperacion) dao.load(TTipoOperacion.class, codTipo);
            operacion.setTTipoOperacion(tipoOper);
            operacion.setEstado("ACTIVO");
            operacion.setNumeroOperacion(numeroOperacion.getNumber(myCodF, myCodC));
            session.setAttribute("NUMERO_OP", operacion.getNumeroOperacion());
            TMoneda monedaObj = (TMoneda) dao.load(TMoneda.class, cod_moneda);
            operacion.setTMoneda(monedaObj);
            operacion.setIdUser(myId);
            operacion.setIpUser(myIp);
            operacion.setDateUser(DateUtil.getNOW_S());
            operacion.setTPersonaCaja(pcaja);
            operacion.setSaldofinal(BigDecimal.ZERO);
            operacion.setSaldoFinalSinInteres(BigDecimal.ZERO);
            operacion.setFd(new Date());
            dao.persist(operacion);
            String str = session.getAttribute("CADENA_SUMA").toString();
            String[] array = str.split(" ");
            for (int i = 0; i < array.length; i++) {
                if (array[i].length() > 0) {
                    TSumaMoneda sum = (TSumaMoneda) dao.load(TSumaMoneda.class, array[i]);
                    sum.setIdoperacion(idForInitNewestTable);
                    dao.update();
                }
            }
            session.setAttribute("CADENA_SUMA", "");
            TRegistroCompraVenta registro = new TRegistroCompraVenta();
            registro.setIdregistrocompraventa(idForInitNewestTable);
            registro.setFecha(DateUtil.getNOW_S());
            registro.setMontoEntregado(entregado);
            registro.setMontoRecibido(recibido);
            registro.setTipoCambio(tasa);
            registro.setTOperacion(operacion);
            registro.setTTipoCambio(tipocambio);
            registro.setIdUser(myId);
            registro.setIpUser(myIp);
            registro.setDateUser(DateUtil.getNOW_S());
            registro.setEstado("ACTIVO");
            registro.setDescuentoDeterioro(new BigDecimal(ddeterioro));
            dao.persist(registro);
            TMoneda mo2 = (TMoneda) dao.load(TMoneda.class, tipocambio.getCodMonedaA());
            //TDetalleCaja detallecaja = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + myCodC + tipocambio.getTMoneda().getCodMoneda() + "00");
            //detallecaja.setMontoFinal(detallecaja.getMontoFinal().add(recibido));
            //detallecaja.setMontoRecibido(detallecaja.getMontoRecibido().add(recibido));
            TLogFinance detallecaja = (TLogFinance) dao.load(TLogFinance.class, "LOG" + myCodC + tipocambio.getTMoneda().getCodMoneda());
            detallecaja.setMontoFinal(detallecaja.getMontoFinal().add(recibido));
            detallecaja.setMontoRecibido(detallecaja.getMontoRecibido().add(recibido));
            detallecaja.setActivoCajaybanco(detallecaja.getActivoCajaybanco().add(recibido));
            detallecaja.setMonto(detallecaja.getMonto().add(recibido));
            dao.update();
            //TDetalleCaja detallecaja2 = (TDetalleCaja) dao.load(TDetalleCaja.class, DateUtil.getDate(new Date()).replaceAll("/", "") + myCodC + mo2.getCodMoneda() + "00");
            //detallecaja2.setMontoFinal(detallecaja2.getMontoFinal().subtract(entregado));
            //detallecaja2.setMontoEntregado(detallecaja2.getMontoEntregado().add(entregado));
            TLogFinance detallecaja2 = (TLogFinance) dao.load(TLogFinance.class, "LOG" + myCodC + mo2.getCodMoneda());
            detallecaja2.setMontoFinal(detallecaja2.getMontoFinal().subtract(entregado));
            detallecaja2.setMontoEntregado(detallecaja2.getMontoEntregado().add(entregado));
            detallecaja2.setActivoCajaybanco(detallecaja2.getActivoCajaybanco().subtract(entregado));
            detallecaja2.setMonto(detallecaja2.getMonto().subtract(entregado));
            dao.update();
            Map ticket = new HashMap();
            ticket.put("FECHA", operacion.getFecha().substring(8, 10) + "/" + operacion.getFecha().substring(5, 8) + operacion.getFecha().substring(0, 4));
            ticket.put("HORA", operacion.getFecha().substring(11));
            ticket.put("RZS", razonSocial);
            ticket.put("TASA", formatTasa(tasa));
            ticket.put("RECIBIDO", formatMoneda.formatMoneda(recibido.doubleValue()));
            ticket.put("ENTREGADO", formatMoneda.formatMoneda(entregado.doubleValue()));
            ticket.put("TIPO", tipo);
            ticket.put("MON", monedaObj.getSimbolo());
            ticket.put("MONEDA", monedaObj.getNombre());
            ticket.put("NOP", operacion.getNumeroOperacion());
            ticket.put("SIMBOLO", monedaObj.getSimbolo());
            ticket.put("COD_OP", cod_moneda);
            ticket.put("D_DETERIORO", ddeterioro);
            session.setAttribute("ticket", ticket);
            response.sendRedirect("vercambiomoneda.htm");
        } finally {
            dao.cerrarSession();
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

    private static String formatTasa(String t) {
        String tasa = t.replace(".", "-");
        String[] array = tasa.split("-");
        if (array[1].length() < 3) {
            t = t + "&nbsp;&nbsp;";
        }
        return t;
    }
}
