package org.finance.bank.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author admin
 */
/***
 *                        Bases de Cálculo para Contabilización de Plazos
 * La contabilización del tiempo transcurrido entre dos fechas (por ejemplo, a efectos del cálculo de
 * intereses) se puede realizar de diferentes formas, en función de las hipótesis asumidas en relación al
 * número de días que median entre esas fechas y al número de días que tiene un año. Son las denominadas
 * Bases de Cálculo para la Contabilización de Plazos.
 *          En relación al número de días entre dos fechas:
 *                    Real (ó Actual): contabilizamos los días de acuerdo con el calendario real.
 *                    30: contabilizamos los días como si todos los meses tuviesen 30 días (Nota 1).
 *          En relación al número de días que forman el año:
 *                    Real (ó Actual): contabilizamos los días de acuerdo con el calendario real, teniendo en
 *                    cuenta el hecho de si estamos trabajando con un año bisiesto o normal.
 *                    365: consideramos que el año tiene 365 días (aunque sea un año bisiesto).
 *                    360: consideramos que el año tiene 360 días.
 * Así pues, podemos considerar seis posibilidades para la contabilización de los días, dos de las cuales no
 * tienen utilización práctica:
 * -------------------------------------------------------------------------------------------------
 *           |         \      No de    |                |                  |                       |
 *           |  No de    \    días del |                |                  |                       |
 *           |  días entre \       año |Real (ó Actual) |      365         |          360          |
 *           |  dos fechas   \         |                |                  |                       |
 * ----------------------------\--------------------------------------------------------------------
 *           |                         |  Real/Real     |     Real/365     |         Real/360      |
 *           |     Real (ó Actual)     | (ó Act/Act)    |   (ó Act/365)    |       (ó Act/360)     |
 *           |                         | [Base = 1]     |   [Base = 3]     |       [Base = 2]      |
 * -------------------------------------------------------------------------------------------------
 *           |                         |                |                  |    Hay dos métodos:   |
 *           |                         |                |                  |    Método Europeo:    |
 *           |                         |                |                  |      30(E)/360        |
 *           |                         | 30/Real        |    30/365        |      [Base = 4]       |
 *           |            30           |                |                  |                       |
 *           |                         |(no utilizada)  |  (no utilizada)  |    Método Americano:  |
 *           |                         |                |                  |          30/360       |
 *           |                         |                |                  |        [Base = 0]     |
 * -------------------------------------------------------------------------------------------------
 * Notas:
 * 1. La referencia a la base (Entre Corchetes) se refiere a la forma en que cada una de ellas se identifica en
 * las hojas se cálculo de Microsoft® Excel.
 * 2. La Base más frecuente utilizada en operaciones de política monetaria del Sistema Euro es Real/360 (Base=2).
 *
 * @author admin
 */
public class PlazosUtil {

    /**
     * Obtener la cantidad de días (Mes = 30 días) en función a 360 dias comerciales
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha final
     * @param NASD Si es True es el Método Americano, sino es Europeo
     *
     */
    //function Dias360(FechaIni,FechaFin: TDate; NASD:Boolean): Integer;
    //
    //var
    //  ai,mi,di,
    //  af,mf,df: Word;
    //
    //begin
    //  DecodeDate(FechaIni, ai, mi, di);
    //  DecodeDate(FechaFin, af, mf, df);
    //  if NASD then // EEUU
    //  begin
    //    if (di = 31) or ((mi = 2)and(di > 27)) then di:= 30;
    //    if (df > 27)and(mf = 2) then df:= 30;
    //    if (df = 31)and(di < 30) then
    //    begin
    //      Inc(mf);
    //      df:= 1;
    //    end
    //    else if df = 31 then
    //     df:= 30
    //  end
    //  else       // EUROPEO
    //  begin
    //    if (di = 31) or ((mi = 2)and(di > 27)) then di:= 30;
    //    if (df = 31) or ((mf = 2)and(df > 27)) then df:= 30;
    //  end;
    //  if Abs(af - ai) = 0 then
    //    Result:= (mf-mi)*30 + df-di
    //  else
    //    Result:= Abs(af-ai-1)*360 + 360-mi*30 + 30-di + 30*(mf-1) + df
    //end;
    public static int getDias30360(Date fechaInicio, Date fechaFin, boolean NASD) {
        int ai, mi, di;
        int af, mf, df;
        Calendar ins1 = Calendar.getInstance();
        Calendar ins2 = Calendar.getInstance();
        ins1.setTime(fechaInicio);
        ins2.setTime(fechaFin);
        ai = ins1.get(java.util.Calendar.YEAR);
        mi = ins1.get(java.util.Calendar.MONTH) + 1;
        di = ins1.get(java.util.Calendar.DATE);
        af = ins2.get(java.util.Calendar.YEAR);
        mf = ins2.get(java.util.Calendar.MONTH) + 1;
        df = ins2.get(java.util.Calendar.DATE);
        if (NASD) {
            if (di == 31 || (mi == 2 && di > 27)) {
                di = 30;
            }
            if (df > 27 && mf == 2) {
                df = 30;
            }
            if (df == 31 && di < 30) {
                mf++;
                df = 1;
            } else if (df == 31) {
                df = 30;
            }
        } else {
            if (di == 31 || (mi == 2 && di > 27)) {
                di = 30;
            }
            if (df == 31 || (mf == 2 && df > 27)) {
                df = 30;
            }
        }
        if (Math.abs(af - ai) == 0) {
            return (mf - mi) * 30 + df - di;
        } else {
            return Math.abs(af - ai - 1) * 360 + 360 - mi * 30 + 30 - di + 30 * (mf - 1) + df;
        }
    }
    /** código Delphi
    function Dias360(Des,Has: TDateTime): Integer;
    var
    dd,dm,dh,da: Integer;
    begin
    if DayOf(Des) = 31 then Des:= IncDay(Des, -1); // mes de 30 días
    if Abs(YearOf(Has)-YearOf(Des)) = 0 then  // mismo año
    begin
    // días que faltan para completar mes (Des)
    dd:= 30 - DayOf(Des);
    // días comprendidos entre meses
    dm:= (Abs(MonthOf(Des)-MonthOf(Has))-1)*30;
    // días transcurridos del mes (Has)
    dh:= DayOf(Has);
    // sumar días
    Result:= dd + dm + dh;
    end
    else    // años diferentes
    begin
    // días transcurridos entre años
    da:= Abs(YearOf(Has)-YearOf(Des)-1)*360;
    // (total dias del 1 año) - dias transcurridos fecha (Des)
    dd:= 360 - MonthOf(Des)*30;
    // total días transcurridos meses fecha Has + los días fecha (Has)
    dm:= (MonthOf(Has)-1)*30 + DayOf(Has);
    // diás faltantes  para completar mes (Des)
    dh:= 30 - DayOf(Des);
    // sumar días
    Result:= da + dd + dm + dh;
    end
    end;
     */
}
