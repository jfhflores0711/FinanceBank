--select log_date, job_name, status, req_start_date, actual_start_date, run_duration from dba_scheduler_job_run_details;
--select job_name, session_id, running_instance, elapsed_time, cpu_used from dba_scheduler_running_jobs;
--select log_date, job_name, status from dba_scheduler_job_log;
/*
create or replace trigger logon_audit_trigger AFTER LOGON ON DATABASE
BEGIN
insert into t_sesion values( to_char(systimestamp, 'yyyymmddhh24missFF'),
to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss'),'LOGON',user ||' =>ID:' || sys_context('USERENV','SESSIONID'),
   sys_context('USERENV','HOST'),   sysdate,(select id_acceso from FINANCEBANK.T_CUENTA_ACCESO where login='admin')
);
END;
/
*//*
CREATE OR REPLACE TRIGGER financebank.logon_audit_trigger
AFTER LOGON  ON DATABASE
BEGIN
if user is not 'SYSMAN' then
insert into t_sesion values(
   to_char(systimestamp, 'yyyymmddhh24missFF'),
to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss'),
'LOGON',user ||' =>ID:' || sys_context('USERENV','SESSIONID'),
   sys_context('USERENV','HOST'),
   sysdate,(select id_acceso from FINANCEBANK.T_CUENTA_ACCESO where login='admin')
);
END;*/
/**/

/*
create or replace trigger logoff_audit_trigger
BEFORE LOGOFF ON DATABASE
BEGIN
update t_sesion set accion = (select action from v$session where sys_context('USERENV','SESSIONID') = audsid)
where user ||' =>ID:' || sys_context('USERENV','SESSIONID') = id_user;
insert into t_sesion values(to_char(systimestamp, 'yyyymmddhh24missFF'),to_char(sysdate, 'yyyy/mm/dd hh24:mi:ss'),
'LOGOFF',user ||' =>ID:' || sys_context('USERENV','SESSIONID'),   sys_context('USERENV','HOST'),
   sysdate,(select id_acceso from FINANCEBANK.T_CUENTA_ACCESO where login='admin')
   where user is not 'SYSMAN'
);

END;*/
/**/

/*
CREATE GLOBAL TEMPORARY TABLE RATE_ONCE
(
ID VARCHAR2(41) NOT NULL,RATE NUMBER(10,2) NOT NULL,
SALDO NUMBER(10,2) NOT NULL,DIAS NUMBER NOT NULL
);
*/

/*
BEGIN
DBMS_JOB.isubmit (job => 99,what => 'my_job_proc(''DBMS_JOB.ISUBMIT Example.'');',next_date => SYSDATE,
interval => 'SYSDATE + 1/24');
COMMIT;
END;
/

--
--  Schedule a snapshot to be run on this instance every hour

variable jobno number;
variable instno number;
begin
  select instance_number into :instno from v$instance;
-- ------------------------------------------------------------
-- Submit job to begin at 0600 and run every hour
-- ------------------------------------------------------------
dbms_job.submit(
   :jobno, 'BEGIN statspack_alert_proc; END;',
   trunc(sysdate)+6/24,
   'trunc(SYSDATE+1/24,''HH'')',
   TRUE,
   :instno);
-- ------------------------------------------------------------
-- Submit job to begin at 0900 and run 12 hours later
-- ------------------------------------------------------------
dbms_job.submit(
   :jobno,'BEGIN statspack_alert_proc; END;',trunc(sysdate+1)+9/24,
   'trunc(SYSDATE+12/24,''HH'')',TRUE,:instno);
-- ------------------------------------------------------------
-- Submit job to begin at 0600 and run every 10 minutes
-- ------------------------------------------------------------
dbms_job.submit(
:jobno,'BEGIN statspack_alert_proc; END;',
trunc(sysdate+1)+6/24,'trunc(sysdate+1/144,''MI'')',
TRUE,:instno);
-- ----------------------------------------------------------
-- Submit job to begin at 0600 and run every hour, Monday - Friday
-- ---------------------------------------------------------
dbms_job.submit(:jobno,'BEGIN statspack_alert_proc; END;',
trunc(sysdate+1)+6/24,trunc(  least(next_day(SYSDATE - 1,'MONDAY'), next_day(SYSDATE - 1,'TUESDAY'),
   next_day(SYSDATE - 1,'WEDNESDAY'), next_day(SYSDATE - 1,'THURSDAY'),next_day(SYSDATE - 1,'FRIDAY'))+1/24,'HH')',TRUE,:instno);
commit;
end;
/
*/
/*
delete from rate_once;
INSERT INTO RATE_ONCE (ID, RATE, SALDO, DIAS) select IDCUENTAPERSONA, (INTERES/100) AS RATE,SALDO,trunc(sysdate - to_date( FECHA_ACTUALIZACION,'yyyy/mm/dd')) AS DIAS  from FINANCEBANK.T_CUENTA_PERSONA where ESTADO = 'ACTIVO' and SALDO >0 and INTERES>0;
select TRUNC(SALDO * POWER((1+(POWER(RATE+1,1/360)-1)),DIAS),2) as saldo from FINANCEBANK.RATE_ONCE where DIAS > 0;


UPDATE (
SELECT (cp.INTERES/100) AS R, cp.SALDO AS S,cp.FECHA_ACTUALIZACION AS F, TRUNC(SYSDATE - TO_DATE(cp.FECHA_ACTUALIZACION,'yyyy/mm/dd')) AS D
FROM FINANCEBANK.T_CUENTA_PERSONA cp
WHERE cp.ESTADO='ACTIVO' AND cp.SALDO > 0 AND cp.INTERES > 0 AND TRUNC(SYSDATE - TO_DATE(cp.FECHA_ACTUALIZACION,'yyyy/mm/dd')) >0)
SET S = TRUNC(S * POWER((1+(POWER(R+1,1/360)-1)),D),2),F=TO_CHAR(SYSDATE,'yyyy/mm/dd') ;

UPDATE (
SELECT (sg.INTERESSG/100) AS R, sg.MONTO_ACTUAL AS S,sg.FECHA_ACTUALIZACION AS F, TRUNC(SYSDATE - TO_DATE(sg.FECHA_ACTUALIZACION,'yyyy/mm/dd')) AS D
FROM FINANCEBANK.T_SOBREGIRO sg
WHERE sg.ESTADO='ACTIVO' AND sg.MONTO_ACTUAL > 0 AND sg.INTERESSG > 0 AND TRUNC(SYSDATE - TO_DATE(sg.FECHA_ACTUALIZACION,'yyyy/mm/dd')) >0)
SET S = TRUNC(S * POWER((1+(POWER(R+1,1/360)-1)),D),2),F=TO_CHAR(SYSDATE,'yyyy/mm/dd') ;
*/

CREATE OR REPLACE PROCEDURE FULL_UPDATE (acc_no IN NUMBER)
IS
BEGIN
UPDATE (
SELECT (cp.INTERES/100) AS R, cp.SALDO AS S,cp.FECHA_ACTUALIZACION AS F, TRUNC(SYSDATE - TO_DATE(cp.FECHA_ACTUALIZACION,'yyyy/mm/dd')) AS D
FROM FINANCEBANK.T_CUENTA_PERSONA cp
WHERE cp.ESTADO='ACTIVO' AND cp.SALDO > 0 AND cp.INTERES > 0 AND TRUNC(SYSDATE - TO_DATE(cp.FECHA_ACTUALIZACION,'yyyy/mm/dd')) >0)
SET S = TRUNC(S * POWER((1+(POWER(R+1,1/360)-1)),D),4),F=TO_CHAR(SYSDATE,'yyyy/mm/dd') ;
UPDATE (
SELECT (sg.INTERESSG/100) AS R, sg.MONTO_ACTUAL AS S,sg.FECHA_ACTUALIZACION AS F, TRUNC(SYSDATE - TO_DATE(sg.FECHA_ACTUALIZACION,'yyyy/mm/dd')) AS D
FROM FINANCEBANK.T_SOBREGIRO sg
WHERE sg.ESTADO='ACTIVO' AND sg.MONTO_ACTUAL > 0 AND sg.INTERESSG > 0 AND TRUNC(SYSDATE - TO_DATE(sg.FECHA_ACTUALIZACION,'yyyy/mm/dd')) >0)
SET S = TRUNC(S * POWER((1+(POWER(R+1,1/360)-1)),D),4),F=TO_CHAR(SYSDATE,'yyyy/mm/dd') ;
END;
/**/

/*
DECLARE
X NUMBER;
BEGIN
    SYS.DBMS_JOB.SUBMIT
(JOB => :X
, WHAT =>'FINANCEBANK.FULL_UPDATE(1);'
, NEXT_DATE => TO_DATE('05/25/2010 00:00:01','mm/dd/yyyy hh24:mi:ss')
, INTERNAL => 'TRUNC(SYSDATE+1)'
, NO_PARSE =>FALSE
);
COMMIT;
END;
/

BEGIN
DBMS_SCHEDULER.CREATE_JOB(
JOB_NAME => 'JOB_UPDATE_SQ'
,JOB_TYPE=> 'STORED_PROCEDURE'
,JOB_ACTION => 'FINANCEBANK.FULL_UPDATE(1);'
,START_DATE => TO_DATE('05/25/2010 00:00:01','mm/dd/yyyy hh24:mi:ss')
,REPEAT_INTERVAL=>'FREQ=DAILY'
,ENABLED => TRUE
,COMMENTS=> 'JOB_UPDATE_SALDOS');
END;
/

VARIABLE jobno NUMBER;
EXEC DBMS_JOB.SUBMIT(:jobno,'FINANCEBANK.FULL_UPDATE(1);',TO_DATE('05/25/2010 00:00:01','mm/dd/yyyy hh24:mi:ss'),'TRUNC(SYSDATE+1)');
/
*/
/*
BEGIN
DBMS_JOB.isubmit (
job => 99,
what => 'FINANCEBANK.FULL_UPDATE(1);',
next_date => TO_DATE('09/26/2010 00:00:01','mm/dd/yyyy hh24:mi:ss'),
interval => 'SYSDATE + 1');

COMMIT;
END;
*/
/**/
ALTER TABLE FINANCEBANK.T_CUENTA_PERSONA ADD FECHA_CAP VARCHAR2 (32) DEFAULT TO_CHAR(SYSDATE,'yyyy/mm/dd');
CREATE OR REPLACE PROCEDURE C_UPDATE (acc_no IN NUMBER)
IS
BEGIN
UPDATE (
SELECT cp.SALDO AS S,cp.FECHA_CAP AS F, TRUNC(SYSDATE - TO_DATE(cp.FECHA_CAP,'yyyy/mm/dd')) AS D, cp.SALDO_SIN_INTERES AS SI
FROM FINANCEBANK.T_CUENTA_PERSONA cp
WHERE cp.ESTADO='ACTIVO' AND cp.SALDO > 0 AND cp.INTERES > 0 AND TRUNC(SYSDATE - TO_DATE(cp.FECHA_ACTUALIZACION,'yyyy/mm/dd')) >29)
SET SI = S,F=TO_CHAR(SYSDATE,'yyyy/mm/dd') ;
END;
/*
BEGIN
DBMS_JOB.isubmit (
job => 990,
what => 'FINANCEBANK.C_UPDATE(1);',
next_date => TO_DATE('09/26/2010 00:00:01','mm/dd/yyyy hh24:mi:ss'),
interval => 'SYSDATE + 30');

COMMIT;
END;
*/