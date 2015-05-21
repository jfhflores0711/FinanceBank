package org.finance.bank.util;

/**
 *
 * @author ronald
 */
public class ReadData {

    /**
     * @param args
     */
    public static void mainT(String[] args) throws Exception {
       // HibernateUtil.getResetTs();
//        for(int i=0;i<99;i++){
//            System.out.println("idx"+i+"="+ DateUtil.convertDateId());
//        }
//        DAOGeneral dao = new DAOGeneral();
//        List lpg = dao.findAll("from TCuentaPersona where TPersona.idUserPk='20100920123534796644'");
//        Iterator i = lpg.iterator();
//        while (i.hasNext()) {
//            TCuentaPersona d=(TCuentaPersona)i.next();
//            TPersona p=(TPersona)dao.load(TPersona.class,"20110517163003803000");
//            d.setTPersona(p);
//            dao.update();
//            TRegistroGiro g = (TRegistroGiro) i.next();
//            if (g.getIdoperacioncobro() == null) {
//                TPersona p = g.getTPersona();
//                TPersona p2 = (TPersona) dao.load(TPersona.class, g.getIdUserPkDestino());
//                if (p.getEstado().equals("INACTIVO")) {
//                    TPersona p1 = (TPersona) dao.load(TPersona.class, "20110404094329213499");
//                    g.setTPersona(p1);
//                    g.setGirador(p.getNombre() + "|" + p.getApellidos());
//                    dao.update();
//                }
//                if (p2 != null) {
//                    if (p2.getEstado().equals("INACTIVO")) {
//                        g.setIdUserPkDestino("20110404094329213499");
//                        g.setRecibidor(p2.getNombre() + "|" + p2.getApellidos());
//                        dao.update();
//                    }
//                } else {
//                    g.setIdUserPkDestino("20110404094329213499");
//                    TPersona p1 = (TPersona) dao.load(TPersona.class, "20110404094329213499");
//                    g.setRecibidor(p1.getNombre() + "|" + p1.getApellidos());
//                    dao.update();
//                }
//            } else {
//                TOperacion o1 = (TOperacion) dao.load(TOperacion.class, g.getIdoperacioncobro());
//                dao.delete(o1);
//                System.out.println("e=" + g.getIdregistro());
//                TOperacion o2 = g.getTOperacion();
//                dao.delete(g);
//                dao.delete(o2);
//            }
//        }
    }
}
