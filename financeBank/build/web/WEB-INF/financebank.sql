ALTER TABLE t_Detalle_Suma DROP CONSTRAINT FK_t_Detalle_Sum_t_Denominacio
;
ALTER TABLE t_Detalle_Suma DROP CONSTRAINT FK_t_Detalle_Sum_t_Suma_Moneda
;
ALTER TABLE t_SobreGiro DROP CONSTRAINT FK_t_SobreGiro_t_Cuenta_Perso
;
ALTER TABLE t_Certificado DROP CONSTRAINT FK_t_Certificado_t_Registro_D
;
ALTER TABLE t_Denominacion_Moneda DROP CONSTRAINT FK_t_Denominacion_Mon_t_Moneda
;
ALTER TABLE t_Registro_Otros DROP CONSTRAINT FK_t_Registro_Ot_t_Persona_Caj
;
ALTER TABLE t_Registro_Otros DROP CONSTRAINT FK_t_Registro_Otro_t_Operacion
;
ALTER TABLE t_Utilidad DROP CONSTRAINT FK_t_Utilidad_t_balancexmoneda
;
ALTER TABLE t_Patrimonio DROP CONSTRAINT FK_t_patrimonio_t_balancexmon
;
ALTER TABLE t_Balancexmoneda DROP CONSTRAINT FK_t_balancexmoneda_t_Moneda
;
ALTER TABLE t_Tasa DROP CONSTRAINT FK_t_tasa_t_Tipo_Cambio
;
ALTER TABLE t_Contrato DROP CONSTRAINT FK_t_Contrato_t_Registro_Pres
;
ALTER TABLE t_Cobranza DROP CONSTRAINT FK_t_Cobranza_t_Detalle_Prest
;
ALTER TABLE t_Cobranza DROP CONSTRAINT FK_t_Cobranza_t_Operacion
;
ALTER TABLE t_Detalle_Prestamo DROP CONSTRAINT FK_t_Detalle_Pre_t_Registro_Pr
;
ALTER TABLE t_Detalle_Cuenta_Persona DROP CONSTRAINT FK_t_Detalle_Cue_t_Cuenta_Pers
;
ALTER TABLE t_Registro_Prestamo DROP CONSTRAINT FK_t_Registro_Pr_t_Cuenta_Pers
;
ALTER TABLE t_Registro_Prestamo DROP CONSTRAINT FK_t_Registro_Pr_t_Tipo_Credit
;
ALTER TABLE t_Registro_Prestamo DROP CONSTRAINT FK_t_Registro_Pres_t_Operacion
;
ALTER TABLE t_Transferencia_Caja DROP CONSTRAINT FK_t_Transferencia_Caja_t_Caja
;
ALTER TABLE t_Transferencia_Caja DROP CONSTRAINT FK_t_Transferencia_Caja_t_Operacion
;
ALTER TABLE t_Sesion DROP CONSTRAINT FK_t_Sesion_Cuenta_Acceso
;
ALTER TABLE t_Control_Tipo DROP CONSTRAINT FK_t_Control_Tip_t_Tipo_Person
;
ALTER TABLE t_Control_Tipo DROP CONSTRAINT FK_t_Control_Tipo_t_Persona
;
ALTER TABLE t_Control_Modulo DROP CONSTRAINT FK_t_Control_Mod_t_Tipo_Person
;
ALTER TABLE t_Control_Modulo DROP CONSTRAINT FK_t_Control_Modulo_Modulo
;
ALTER TABLE t_Provincia DROP CONSTRAINT FK_Provincia_Departamento
;
ALTER TABLE t_Distrito DROP CONSTRAINT FK_Distrito_Provincia
;
ALTER TABLE t_Tipo_Cambio DROP CONSTRAINT FK_Tipo_Cambio_Moneda
;
ALTER TABLE t_Registro_Compra_Venta DROP CONSTRAINT FK_Registro_Compra_Tipo_Cambio
;
ALTER TABLE t_Registro_Compra_Venta DROP CONSTRAINT FK_Registro_Compra_V_Operacion
;
ALTER TABLE t_Registro_Giro DROP CONSTRAINT FK_Registro_Giro_Operacion
;
ALTER TABLE t_Registro_Giro DROP CONSTRAINT FK_t_Registro_Giro_t_filial
;
ALTER TABLE t_Registro_Giro DROP CONSTRAINT FK_t_Registro_Giro_t_Persona
;
ALTER TABLE t_Operacion DROP CONSTRAINT FK_Operacion_Moneda
;
ALTER TABLE t_Operacion DROP CONSTRAINT FK_t_Operacion_t_Persona_Caja
;
ALTER TABLE t_Operacion DROP CONSTRAINT FK_Operacion_Tipo_Operacion
;
ALTER TABLE t_Registro_Deposito_Retiro DROP CONSTRAINT FK_Registro_Deposito_Operacion
;
ALTER TABLE t_Registro_Deposito_Retiro DROP CONSTRAINT FK_t_Registro_De_t_Cuenta_Pers
;
ALTER TABLE t_Detalle_Caja DROP CONSTRAINT FK_Detalle_Caja_Caja
;
ALTER TABLE t_Detalle_Caja DROP CONSTRAINT FK_t_Detalle_Caja_t_Moneda
;
ALTER TABLE t_Filial DROP CONSTRAINT FK_t_filial_t_Distrito
;
ALTER TABLE t_Caja DROP CONSTRAINT FK_Caja_filial
;
ALTER TABLE t_Persona_Caja DROP CONSTRAINT FK_Persona_Caja_Caja
;
ALTER TABLE t_Persona_Caja DROP CONSTRAINT FK_t_Persona_Caja_t_Persona
;
ALTER TABLE t_Cuenta_Acceso DROP CONSTRAINT FK_t_Cuenta_Acceso_t_Persona
;
ALTER TABLE t_Persona DROP CONSTRAINT FK_Persona_t_Categoria_Persona
;
ALTER TABLE t_Cuenta_Persona DROP CONSTRAINT FK_Cuenta_Persona_Moneda
;
ALTER TABLE t_Cuenta_Persona DROP CONSTRAINT FK_t_Cuenta_Persona_t_Persona
;
ALTER TABLE t_Cuenta_Persona DROP CONSTRAINT FK_Cuenta_Persona_Tipo_Cuenta
;

DROP TABLE t_Log_finance
;
DROP TABLE t_Transaccion_c CASCADE CONSTRAINTS
;
DROP TABLE t_Suma_Moneda CASCADE CONSTRAINTS
;
DROP TABLE t_Detalle_Suma CASCADE CONSTRAINTS
;
DROP TABLE t_SobreGiro CASCADE CONSTRAINTS
;
DROP TABLE t_Certificado CASCADE CONSTRAINTS
;
DROP TABLE t_Tipo_Credito CASCADE CONSTRAINTS
;
DROP TABLE t_Denominacion_Moneda CASCADE CONSTRAINTS
;
DROP TABLE t_Registro_Otros CASCADE CONSTRAINTS
;
DROP TABLE t_Utilidad CASCADE CONSTRAINTS
;
DROP TABLE t_patrimonio CASCADE CONSTRAINTS
;
DROP TABLE t_balancexmoneda CASCADE CONSTRAINTS
;
DROP TABLE t_tasa CASCADE CONSTRAINTS
;
DROP TABLE t_Contrato CASCADE CONSTRAINTS
;
DROP TABLE t_Cobranza CASCADE CONSTRAINTS
;
DROP TABLE t_Detalle_Prestamo CASCADE CONSTRAINTS
;
DROP TABLE t_Detalle_Cuenta_Persona CASCADE CONSTRAINTS
;
DROP TABLE t_Registro_Prestamo CASCADE CONSTRAINTS
;
DROP TABLE t_Transferencia_Caja CASCADE CONSTRAINTS
;
DROP TABLE t_Sesion CASCADE CONSTRAINTS
;
DROP TABLE t_Categoria_Persona CASCADE CONSTRAINTS
;
DROP TABLE t_Control_Tipo CASCADE CONSTRAINTS
;
DROP TABLE t_Control_Modulo CASCADE CONSTRAINTS
;
DROP TABLE t_Departamento CASCADE CONSTRAINTS
;
DROP TABLE t_Provincia CASCADE CONSTRAINTS
;
DROP TABLE t_Distrito CASCADE CONSTRAINTS
;
DROP TABLE t_Moneda CASCADE CONSTRAINTS
;
DROP TABLE t_Tipo_Cambio CASCADE CONSTRAINTS
;
DROP TABLE t_Tipo_Operacion CASCADE CONSTRAINTS
;
DROP TABLE t_Registro_Compra_Venta CASCADE CONSTRAINTS
;
DROP TABLE t_Registro_Giro CASCADE CONSTRAINTS
;
DROP TABLE t_Operacion CASCADE CONSTRAINTS
;
DROP TABLE t_Registro_Deposito_Retiro CASCADE CONSTRAINTS
;
DROP TABLE t_Detalle_Caja CASCADE CONSTRAINTS
;
DROP TABLE t_filial CASCADE CONSTRAINTS
;
DROP TABLE t_Caja CASCADE CONSTRAINTS
;
DROP TABLE t_Persona_Caja CASCADE CONSTRAINTS
;
DROP TABLE t_Modulo CASCADE CONSTRAINTS
;
DROP TABLE t_Cuenta_Acceso CASCADE CONSTRAINTS
;
DROP TABLE t_Tipo_Persona CASCADE CONSTRAINTS
;
DROP TABLE t_Persona CASCADE CONSTRAINTS
;
DROP TABLE t_Cuenta_Persona CASCADE CONSTRAINTS
;
DROP TABLE t_Tipo_Cuenta CASCADE CONSTRAINTS
;
/*
*/

CREATE TABLE t_Log_finance (
	idlogfinance varchar(50) NOT NULL,
	tipo_operacion varchar(50) NOT NULL,
	referencia varchar(250),
	monto decimal(10,4) NOT NULL,
	patrimonio decimal(20,4) NOT NULL,
	activo_cajaybanco decimal(20,4) NOT NULL,
	activo_cuentaxcobrar decimal(20,4),
	pasivo decimal(20,4) NOT NULL,
	encaje decimal(20,4) NOT NULL,
	p_respaldo decimal(20,4),
	fecha varchar(50),
	cajero varchar(50),
	observacion varchar(50),
	filial varchar(50)
)
;

CREATE TABLE t_Transaccion (
	idtransaccion varchar(41) NOT NULL,
	tipo_operacion varchar(50),
	monto decimal(20,4),
	fecha varchar(50),
	idCuenta varchar(41),
	user_login varchar(50),
	nombre_referencia varchar(200),
	apellido_referencia varchar(200),
	numero_cheque varchar(20),
        saldo decimal(20,4) DEFAULT 0
)
;

CREATE TABLE t_Transaccion_c (
	idtranscapita varchar(41) NOT NULL,
	tipo_operacion varchar(50),
	fecha varchar(50),
	fechaultima varchar(50)
)
;

CREATE TABLE t_Detalle_Suma (
	iddetallesuma varchar(50) NOT NULL,
	cantidad integer,
	estado varchar(50),
	iddenominacionmoneda varchar(50) NOT NULL,
	idsumamoneda varchar(50) NOT NULL
)
;

CREATE TABLE t_SobreGiro (
	idsobregiro varchar(41) NOT NULL,
	monto_actual decimal(20,4),
	interessg decimal(20,4),
	fecha_pago varchar(50),
	estado varchar(10),
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	idCuentaPersona varchar(41) NOT NULL,
	monto_sin_interes decimal(20,4),
	fecha_actualizacion varchar(50),
	fecha_cap varchar(50)
)
;

CREATE TABLE t_Certificado (
	idcertificado varchar(41) NOT NULL,
	fecha varchar(50),
	num_certificado varchar(40),
	dni_ruc varchar(20),
	nombre varchar(120),
	nombre_rep varchar(100),
	descripcion varchar(4000),
	estado varchar(20),
	tipo varchar(20),
	iddepositoretiro varchar(41) NOT NULL
)
;

CREATE TABLE t_Tipo_Credito (
	idtipocredito varchar(41) NOT NULL,
	nombre varchar(50) NOT NULL,
	descripcion varchar(150),
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL
)
;

CREATE TABLE t_Denominacion_Moneda (
	iddenominacionmoneda varchar(41) NOT NULL,
	tipo varchar(50) NOT NULL,
	monto varchar(20) NOT NULL,
	imagen varchar(500),
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_moneda varchar(41) NOT NULL,
	orden integer
)
;

CREATE TABLE t_Registro_Otros (
	idregistrootros varchar(41) NOT NULL,
	descripcion varchar(500) NOT NULL,
	monto decimal(20,2) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50),
	date_user varchar(50),
	id_operacion varchar(41),
	idpersonacaja varchar(41) NOT NULL
)
;

CREATE TABLE t_Utilidad (
	idutilidad varchar(41) NOT NULL,
	utilidad decimal(20,4) NOT NULL,
	fecha varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50),
	date_user varchar(50),
	idbalance varchar(41) NOT NULL
)
;

CREATE TABLE t_Patrimonio (
	idpatrimonio varchar(41) NOT NULL,
	patrimonio decimal(20,4) NOT NULL,
	patrimonio_actual decimal(20,4) NOT NULL,
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	idbalance varchar(41) NOT NULL,
	tipo_variacion varchar(20)
)
;

CREATE TABLE t_Balancexmoneda (
	idbalance varchar(41) NOT NULL,
	activo_cajaybanco decimal(20,4) NOT NULL,
	activo_cuentaxcobrar decimal(20,4) NOT NULL,
	pasivo decimal(20,4) NOT NULL,
	encaje decimal(20,4) NOT NULL,
	p_respaldo decimal(20,4) NOT NULL,
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_moneda varchar(41) NOT NULL
)
;

CREATE TABLE t_Tasa (
	idtasa varchar(41) NOT NULL,
	f_conversion decimal(20,4) NOT NULL,
	fecha varchar(50) NOT NULL,
	estado varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	idtipocambio varchar(41) NOT NULL,
	tipo_tasa varchar(50)
)
;

CREATE TABLE t_Contrato (
	idcontrato varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	num_contrato varchar(40),
	dni_ruc varchar(20) NOT NULL,
	nombre varchar(120) NOT NULL,
	nombre_rep varchar(100) NOT NULL,
	descripcion varchar(4000) NOT NULL,
	idprestamo varchar(41) NOT NULL,
	estado varchar(20) NOT NULL
)
;

CREATE TABLE t_Cobranza (
	idcobranza varchar(41) NOT NULL,
	idoperacion varchar(41) NOT NULL,
	iddetalleprestamo varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(50) NOT NULL
)
;

CREATE TABLE t_Detalle_Prestamo (
	iddetalleprestamo varchar(41) NOT NULL,
	numero_cuota integer NOT NULL,
	monto_capital decimal(20,4) NOT NULL,
	monto_total decimal(20,4) NOT NULL,
	fecha_pago varchar(50) NOT NULL,
	interes_prestamo decimal(12,4) NOT NULL,
	interes_moratorio decimal(12,4) NOT NULL,
	idprestamo varchar(41) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(50),
	capital_pendiente decimal(20,4) NOT NULL,
	interes_comp varchar(10),
	comision varchar(10),
	itf varchar(10)
)
;

CREATE TABLE t_Detalle_Cuenta_Persona (
	iddetallecuentapersona varchar(41) NOT NULL,
	interes decimal(20,4) NOT NULL,
	fecha_plazo varchar(50) NOT NULL,
	idCuentaPersona varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	num_dias varchar(10)
)
;

CREATE TABLE t_Registro_Prestamo (
	idprestamo varchar(41) NOT NULL,
	monto decimal(20,2) NOT NULL,
	fecha varchar(50) NOT NULL,
	numero_cuotas integer NOT NULL,
	interes_prestamo decimal(12,4) NOT NULL,
	interes_moratorio decimal(12,4),
	idoperacion varchar(41) NOT NULL,
	idcuentapersona varchar(41) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(20) NOT NULL,
	tea varchar(10),
	seg_desg varchar(10),
	periodo varchar(10),
	idtipocredito varchar(41) NOT NULL,
	itf varchar(10)
)
;

CREATE TABLE t_Transferencia_Caja (
	idtransferenciacaja varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	monto decimal(20,4) NOT NULL,
	descripcion varchar(500),
	tipo varchar(20) NOT NULL,
	cod_caja_destino varchar(10) NOT NULL,
	cod_caja varchar(10) NOT NULL,
	ip_user varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	id_operacion varchar(41) NOT NULL
)
;

CREATE TABLE t_Sesion (
	idsession varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	accion varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(41) NOT NULL,
	date_user varchar(50) NOT NULL,
	id_acceso varchar(41) NOT NULL
)
;

CREATE TABLE t_Categoria_Persona (
	idcategoriapersona varchar(41) NOT NULL,
	descripcion varchar(100) NOT NULL
)
;

CREATE TABLE t_Control_Tipo (
	idcontroltipo varchar(41) NOT NULL,
	id_user_pk varchar(50) NOT NULL,
	idTipoPersona varchar(41) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(20) NOT NULL
)
;

CREATE TABLE t_Control_Modulo (
	idcontrolmodulo varchar(41) NOT NULL,
	idmodulo varchar(41) NOT NULL,
	idtipopersona varchar(41) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(20) NOT NULL
)
;

CREATE TABLE t_Departamento (
	iddepartamento varchar(41) NOT NULL,
	codigo varchar(50) NOT NULL,
	descripcion varchar(200) NOT NULL
)
;

CREATE TABLE t_Provincia (
	idprovincia varchar(41) NOT NULL,
	codigo varchar(50) NOT NULL,
	descripcion varchar(200),
	iddepartamento varchar(41) NOT NULL
)
;

CREATE TABLE t_Distrito (
	iddistrito varchar(41) NOT NULL,
	codigo varchar(50) NOT NULL,
	descripcion varchar(200) NOT NULL,
	idprovincia varchar(41) NOT NULL
)
;

CREATE TABLE t_Moneda (
	cod_moneda varchar(41) NOT NULL,
	nombre varchar(20) NOT NULL,
	simbolo varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_para_num_cuenta varchar(10) NOT NULL,
	color varchar(20),
	estado varchar(10)
)
;

CREATE TABLE t_Tipo_Cambio (
	idtipocambio varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	cod_moneda_a varchar(41) NOT NULL,
	descripcion varchar(100),
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_moneda varchar(50) NOT NULL
)
;

CREATE TABLE t_Tipo_Operacion (
	codigo_tipo_operacion varchar(41) NOT NULL,
	nombre varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL
)
;

CREATE TABLE t_Registro_Compra_Venta (
	idregistrocompraventa varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	monto_entregado decimal(20,2) NOT NULL,
	monto_recibido decimal(20,2) NOT NULL,
	tipo_cambio varchar(20) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	id_operacion varchar(41) NOT NULL,
	idtipocambio varchar(41) NOT NULL,
	estado varchar(50),
	descuento_deterioro decimal(8,2)
)
;

CREATE TABLE t_Registro_Giro (
	idregistro varchar(41) NOT NULL,
	id_user_pk_origen varchar(50) NOT NULL,
	id_user_pk_destino varchar(50),
	fecha varchar(50) NOT NULL,
	importe decimal(20,4) NOT NULL,
	fpago_importe varchar(20),
	comision decimal(20,4),
	fpago_comision varchar(20),
	fecha_entrega varchar(20) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(50) NOT NULL,
	id_operacion varchar(41) NOT NULL,
	cod_filial varchar(41) NOT NULL,
	pagador_comision varchar(100),
	idcuentapersona varchar(41),
	idoperacioncobro varchar(41)
)
;

CREATE TABLE t_Operacion (
	id_operacion varchar(41) NOT NULL,
	numero_operacion varchar(50) NOT NULL,
	descripcion varchar(100),
	fecha varchar(50) NOT NULL,
	codigo_tipo_operacion varchar(41) NOT NULL,
	cod_moneda varchar(41) NOT NULL,
	estado varchar(100) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	idpersonacaja varchar(41) NOT NULL,
	fecha_extornacion varchar(41),
	id_admin_extorno varchar(41)
)
;

CREATE TABLE t_Registro_Deposito_Retiro (
	iddepositoretiro varchar(41) NOT NULL,
	fecha varchar(50) NOT NULL,
	num_cta varchar(30) NOT NULL,
	importe decimal(20,2) NOT NULL,
	nombre_representante varchar(200),
	apellidos_representante varchar(200),
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	id_operacion varchar(41) NOT NULL,
	idcuentapersona varchar(41) NOT NULL,
	numero_cheque varchar(20),
	num_dias varchar(10),
	dni_representante varchar(12),
	fecha_nac_representante varchar(50)
)
;

CREATE TABLE t_Detalle_Caja (
	iddetallecaja varchar(41) NOT NULL,
	monto_inicial decimal(20,2),
	monto_final decimal(20,2),
	fecha_transaccion varchar(50) NOT NULL,
	cod_caja varchar(10) NOT NULL,
	cod_moneda varchar(41) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	estado varchar(10)
)
;

CREATE TABLE t_Filial (
	cod_filial varchar(10) NOT NULL,
	nombre varchar(100) NOT NULL,
	direccion varchar(100),
	estado varchar(10) NOT NULL,
	telefono varchar(10),
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	iddistrito varchar(41) NOT NULL,
	cod_para_num_cuenta varchar(10) NOT NULL
)
;

CREATE TABLE t_Caja (
	cod_caja varchar(10) NOT NULL,
	nombre_caja varchar(50) NOT NULL,
	tipo varchar(20) NOT NULL,
	estado varchar(10) NOT NULL,
	ip_user varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_filial varchar(10) NOT NULL
)
;

CREATE TABLE t_Persona_Caja (
	idpersonacaja varchar(41) NOT NULL,
	id_user_pk varchar(50) NOT NULL,
	cod_caja varchar(10) NOT NULL,
	estado varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL
)
;

CREATE TABLE t_Modulo (
	idmodulo varchar(41) NOT NULL,
	descripcion varchar(200) NOT NULL,
	url varchar(50),
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL
)
;

CREATE TABLE t_Cuenta_Acceso (
	id_acceso varchar(41) NOT NULL,
	login varchar(50) NOT NULL,
	contrasenia varchar(50) NOT NULL,
	ip_acceso varchar(500) NOT NULL,
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	id_user_pk varchar(50) NOT NULL
)
;

CREATE TABLE t_Tipo_Persona (
	idtipopersona varchar(41) NOT NULL,
	descripcion varchar(200) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_User varchar(50) NOT NULL
)
;

CREATE TABLE t_Persona (
	id_user_pk varchar(50) NOT NULL,
	doc_identidad varchar(10),
	nombre varchar(60) NOT NULL,
	apellidos varchar(60) NOT NULL,
	email varchar(50),
	ubigeo varchar(10),
	telefono varchar(100),
	celular varchar(100),
	url_foto varchar(200),
	url_firma varchar(200),
	direccion varchar(100),
	estado varchar(10) NOT NULL,
	id_user varchar(50) NOT NULL,
	ip_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	idCategoriaPersona varchar(41) NOT NULL,
	ruc varchar(50)
)
;

CREATE TABLE t_Cuenta_Persona (
	idcuentapersona varchar(41) NOT NULL,
	num_cta varchar(50) NOT NULL,
	fecha varchar(50) NOT NULL,
	estado varchar(10) NOT NULL,
	observaciones varchar(500),
	ip_user varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	codigo_cuenta varchar(10) NOT NULL,
	id_user_pk varchar(50) NOT NULL,
	interes decimal(20,4),
	cod_moneda varchar(41) NOT NULL,
	saldo decimal(20,4),
	saldo_sin_interes decimal(20,4),
	fecha_actualizacion varchar(50),
	fecha_cap varchar(50)
)
;

CREATE TABLE t_Tipo_Cuenta (
	codigo_cuenta varchar(10) NOT NULL,
	descripcion varchar(100) NOT NULL,
	ip_user varchar(50) NOT NULL,
	id_user varchar(50) NOT NULL,
	date_user varchar(50) NOT NULL,
	cod_para_num_cuenta varchar(10) NOT NULL
)
;


ALTER TABLE t_Log_finance ADD CONSTRAINT PK_t_Log_finance
	PRIMARY KEY (idlogfinance)
;


ALTER TABLE t_Transaccion_c ADD CONSTRAINT PK_t_Transaccion_c
	PRIMARY KEY (idtranscapita)
;


ALTER TABLE t_Transaccion ADD CONSTRAINT PK_t_Transaccion
	PRIMARY KEY (idtransaccion)
;

ALTER TABLE t_Suma_Moneda ADD CONSTRAINT PK_t_Suma_Moneda
	PRIMARY KEY (idsumamoneda)
;

ALTER TABLE t_Detalle_Suma ADD CONSTRAINT PK_t_Suma_Monedas
	PRIMARY KEY (iddetallesuma)
;

ALTER TABLE t_SobreGiro ADD CONSTRAINT PK_t_SobreGiro
	PRIMARY KEY (idsobregiro)
;

ALTER TABLE t_Certificado ADD CONSTRAINT PK_t_Certificado
	PRIMARY KEY (idcertificado)
;

ALTER TABLE t_Tipo_Credito ADD CONSTRAINT PK_t_Tipo_Credito
	PRIMARY KEY (idtipocredito)
;

ALTER TABLE t_Denominacion_Moneda ADD CONSTRAINT PK_t_Denominacion_Moneda
	PRIMARY KEY (iddenominacionmoneda)
;

ALTER TABLE t_Registro_Otros ADD CONSTRAINT PK_t_Registro_Otros
	PRIMARY KEY (idregistrootros)
;

ALTER TABLE t_Utilidad ADD CONSTRAINT PK_t_Utilidad
	PRIMARY KEY (idutilidad)
;

ALTER TABLE t_Patrimonio ADD CONSTRAINT PK_t_patrimonio
	PRIMARY KEY (idpatrimonio)
;

ALTER TABLE t_Balancexmoneda ADD CONSTRAINT PK_t_balancexmoneda
	PRIMARY KEY (idbalance)
;

ALTER TABLE t_Tasa ADD CONSTRAINT PK_t_tasa
	PRIMARY KEY (idtasa)
;

ALTER TABLE t_Contrato ADD CONSTRAINT PK_t_Contrato
	PRIMARY KEY (idcontrato)
;

ALTER TABLE t_Cobranza ADD CONSTRAINT PK_t_Cobranza
	PRIMARY KEY (idcobranza)
;

ALTER TABLE t_Detalle_Prestamo ADD CONSTRAINT PK_t_Detalle_Prestamo
	PRIMARY KEY (iddetalleprestamo)
;

ALTER TABLE t_Detalle_Cuenta_Persona ADD CONSTRAINT PK_t_Detalle_Cuenta_Persona
	PRIMARY KEY (iddetallecuentapersona)
;

ALTER TABLE t_Registro_Prestamo ADD CONSTRAINT PK_t_prestamo
	PRIMARY KEY (idprestamo)
;

ALTER TABLE t_Transferencia_Caja ADD CONSTRAINT PK_t_Transferencia_Caja
	PRIMARY KEY (idtransferenciacaja)
;

ALTER TABLE t_Sesion ADD CONSTRAINT PK_t_Sesion
	PRIMARY KEY (idsession)
;

ALTER TABLE t_Categoria_Persona ADD CONSTRAINT PK_t_Categoria_Persona
	PRIMARY KEY (idcategoriapersona)
;

ALTER TABLE t_Control_Tipo ADD CONSTRAINT PK_t_Control_Tipo
	PRIMARY KEY (idcontroltipo)
;

ALTER TABLE t_Control_Modulo ADD CONSTRAINT PK_t_Control_Modulo
	PRIMARY KEY (idcontrolmodulo)
;

ALTER TABLE t_Departamento ADD CONSTRAINT PK_Departamento
	PRIMARY KEY (iddepartamento)
;

ALTER TABLE t_Provincia ADD CONSTRAINT PK_Provincia
	PRIMARY KEY (idprovincia)
;

ALTER TABLE t_Distrito ADD CONSTRAINT PK_Distrito
	PRIMARY KEY (iddistrito)
;

ALTER TABLE t_Moneda ADD CONSTRAINT PK_Moneda
	PRIMARY KEY (cod_moneda)
;

ALTER TABLE t_Tipo_Cambio ADD CONSTRAINT PK_Tipo_Cambio
	PRIMARY KEY (idtipocambio)
;

ALTER TABLE t_Tipo_Operacion ADD CONSTRAINT PK_Tipo_Operacion
	PRIMARY KEY (codigo_tipo_operacion)
;

ALTER TABLE t_Registro_Compra_Venta ADD CONSTRAINT PK_Registro_Compra_Venta
	PRIMARY KEY (idregistrocompraventa)
;

ALTER TABLE t_Registro_Giro ADD CONSTRAINT PK_Registro_Giro
	PRIMARY KEY (idregistro)
;

ALTER TABLE t_Operacion ADD CONSTRAINT PK_Operacion
	PRIMARY KEY (id_operacion)
;

ALTER TABLE t_Registro_Deposito_Retiro ADD CONSTRAINT PK_Registro_Deposito_Retiro
	PRIMARY KEY (iddepositoretiro)
;

ALTER TABLE t_Detalle_Caja ADD CONSTRAINT PK_Detalle_Caja
	PRIMARY KEY (iddetallecaja)
;

ALTER TABLE t_Filial ADD CONSTRAINT PK_filial
	PRIMARY KEY (cod_filial)
;

ALTER TABLE t_Caja ADD CONSTRAINT PK_Caja
	PRIMARY KEY (cod_caja)
;

ALTER TABLE t_Persona_Caja ADD CONSTRAINT PK_Persona_Caja
	PRIMARY KEY (idpersonacaja)
;

ALTER TABLE t_Modulo ADD CONSTRAINT PK_Modulo
	PRIMARY KEY (idmodulo)
;

ALTER TABLE t_Cuenta_Acceso ADD CONSTRAINT PK_Cuenta_Acceso
	PRIMARY KEY (id_acceso)
;

ALTER TABLE t_Tipo_Persona ADD CONSTRAINT PK_Tipo_Persona
	PRIMARY KEY (idtipopersona)
;

ALTER TABLE t_Persona ADD CONSTRAINT PK_t_Persona
	PRIMARY KEY (id_user_pk)
;

ALTER TABLE t_Cuenta_Persona ADD CONSTRAINT PK_Cuenta_Persona
	PRIMARY KEY (idcuentapersona)
;

ALTER TABLE t_Tipo_Cuenta ADD CONSTRAINT PK_Tipo_Cuenta
	PRIMARY KEY (codigo_cuenta)
;



ALTER TABLE t_Detalle_Suma ADD CONSTRAINT FK_t_Detalle_Sum_t_Denominacio
	FOREIGN KEY (iddenominacionmoneda) REFERENCES t_Denominacion_Moneda (iddenominacionmoneda)
;

ALTER TABLE t_Detalle_Suma ADD CONSTRAINT FK_t_Detalle_Sum_t_Suma_Moneda
	FOREIGN KEY (idsumamoneda) REFERENCES t_Suma_Moneda (idsumamoneda)
;

ALTER TABLE t_SobreGiro ADD CONSTRAINT FK_t_SobreGiro_t_Cuenta_Perso
	FOREIGN KEY (idCuentaPersona) REFERENCES t_Cuenta_Persona (idcuentapersona)
;

ALTER TABLE t_Certificado ADD CONSTRAINT FK_t_Certificado_t_Registro_D
	FOREIGN KEY (iddepositoretiro) REFERENCES t_Registro_Deposito_Retiro (iddepositoretiro)
;

ALTER TABLE t_Denominacion_Moneda ADD CONSTRAINT FK_t_Denominacion_Mon_t_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Registro_Otros ADD CONSTRAINT FK_t_Registro_Ot_t_Persona_Caj
	FOREIGN KEY (idpersonacaja) REFERENCES t_Persona_Caja (idpersonacaja)
;

ALTER TABLE t_Registro_Otros ADD CONSTRAINT FK_t_Registro_Otro_t_Operacion
	FOREIGN KEY (id_operacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Utilidad ADD CONSTRAINT FK_t_Utilidad_t_balancexmoneda
	FOREIGN KEY (idbalance) REFERENCES t_Balancexmoneda (idbalance)
;

ALTER TABLE t_Patrimonio ADD CONSTRAINT FK_t_patrimonio_t_balancexmon
	FOREIGN KEY (idbalance) REFERENCES t_Balancexmoneda (idbalance)
;

ALTER TABLE t_Balancexmoneda ADD CONSTRAINT FK_t_balancexmoneda_t_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Tasa ADD CONSTRAINT FK_t_tasa_t_Tipo_Cambio
	FOREIGN KEY (idtipocambio) REFERENCES t_Tipo_Cambio (idtipocambio)
;

ALTER TABLE t_Contrato ADD CONSTRAINT FK_t_Contrato_t_Registro_Pres
	FOREIGN KEY (idprestamo) REFERENCES t_Registro_Prestamo (idprestamo)
;

ALTER TABLE t_Cobranza ADD CONSTRAINT FK_t_Cobranza_t_Detalle_Prest
	FOREIGN KEY (iddetalleprestamo) REFERENCES t_Detalle_Prestamo (iddetalleprestamo)
;

ALTER TABLE t_Cobranza ADD CONSTRAINT FK_t_Cobranza_t_Operacion
	FOREIGN KEY (idoperacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Detalle_Prestamo ADD CONSTRAINT FK_t_Detalle_Pre_t_Registro_Pr
	FOREIGN KEY (idprestamo) REFERENCES t_Registro_Prestamo (idprestamo)
;

ALTER TABLE t_Detalle_Cuenta_Persona ADD CONSTRAINT FK_t_Detalle_Cue_t_Cuenta_Pers
	FOREIGN KEY (idCuentaPersona) REFERENCES t_Cuenta_Persona (idcuentapersona)
;

ALTER TABLE t_Registro_Prestamo ADD CONSTRAINT FK_t_Registro_Pr_t_Cuenta_Pers
	FOREIGN KEY (idcuentapersona) REFERENCES t_Cuenta_Persona (idcuentapersona)
;

ALTER TABLE t_Registro_Prestamo ADD CONSTRAINT FK_t_Registro_Pr_t_Tipo_Credit
	FOREIGN KEY (idtipocredito) REFERENCES t_Tipo_Credito (idtipocredito)
;

ALTER TABLE t_Registro_Prestamo ADD CONSTRAINT FK_t_Registro_Pres_t_Operacion
	FOREIGN KEY (idoperacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Transferencia_Caja ADD CONSTRAINT FK_t_Transferencia_Caja_t_Caja
	FOREIGN KEY (cod_caja) REFERENCES t_Caja (cod_caja)
;

ALTER TABLE t_Transferencia_Caja ADD CONSTRAINT FK_t_Transferencia_Caja_t_Operacion
	FOREIGN KEY (id_operacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Sesion ADD CONSTRAINT FK_t_Sesion_Cuenta_Acceso
	FOREIGN KEY (id_acceso) REFERENCES t_Cuenta_Acceso (id_acceso)
;

ALTER TABLE t_Control_Tipo ADD CONSTRAINT FK_t_Control_Tip_t_Tipo_Person
	FOREIGN KEY (idTipoPersona) REFERENCES t_Tipo_Persona (idtipopersona)
;

ALTER TABLE t_Control_Tipo ADD CONSTRAINT FK_t_Control_Tipo_t_Persona
	FOREIGN KEY (id_user_pk) REFERENCES t_Persona (id_user_pk)
;

ALTER TABLE t_Control_Modulo ADD CONSTRAINT FK_t_Control_Mod_t_Tipo_Person
	FOREIGN KEY (idtipopersona) REFERENCES t_Tipo_Persona (idtipopersona)
;

ALTER TABLE t_Control_Modulo ADD CONSTRAINT FK_t_Control_Modulo_Modulo
	FOREIGN KEY (idmodulo) REFERENCES t_Modulo (idmodulo)
;

ALTER TABLE t_Provincia ADD CONSTRAINT FK_Provincia_Departamento
	FOREIGN KEY (iddepartamento) REFERENCES t_Departamento (iddepartamento)
;

ALTER TABLE t_Distrito ADD CONSTRAINT FK_Distrito_Provincia
	FOREIGN KEY (idprovincia) REFERENCES t_Provincia (idprovincia)
;

ALTER TABLE t_Tipo_Cambio ADD CONSTRAINT FK_Tipo_Cambio_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Registro_Compra_Venta ADD CONSTRAINT FK_Registro_Compra_Tipo_Cambio
	FOREIGN KEY (idtipocambio) REFERENCES t_Tipo_Cambio (idtipocambio)
;

ALTER TABLE t_Registro_Compra_Venta ADD CONSTRAINT FK_Registro_Compra_V_Operacion
	FOREIGN KEY (id_operacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Registro_Giro ADD CONSTRAINT FK_Registro_Giro_Operacion
	FOREIGN KEY (id_operacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Registro_Giro ADD CONSTRAINT FK_t_Registro_Giro_t_filial
	FOREIGN KEY (cod_filial) REFERENCES t_Filial (cod_filial)
;

ALTER TABLE t_Registro_Giro ADD CONSTRAINT FK_t_Registro_Giro_t_Persona
	FOREIGN KEY (id_user_pk_origen) REFERENCES t_Persona (id_user_pk)
;

ALTER TABLE t_Operacion ADD CONSTRAINT FK_Operacion_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Operacion ADD CONSTRAINT FK_t_Operacion_t_Persona_Caja
	FOREIGN KEY (idpersonacaja) REFERENCES t_Persona_Caja (idpersonacaja)
;

ALTER TABLE t_Operacion ADD CONSTRAINT FK_Operacion_Tipo_Operacion
	FOREIGN KEY (codigo_tipo_operacion) REFERENCES t_Tipo_Operacion (codigo_tipo_operacion)
;

ALTER TABLE t_Registro_Deposito_Retiro ADD CONSTRAINT FK_Registro_Deposito_Operacion
	FOREIGN KEY (id_operacion) REFERENCES t_Operacion (id_operacion)
;

ALTER TABLE t_Registro_Deposito_Retiro ADD CONSTRAINT FK_t_Registro_De_t_Cuenta_Pers
	FOREIGN KEY (idcuentapersona) REFERENCES t_Cuenta_Persona (idcuentapersona)
;

ALTER TABLE t_Detalle_Caja ADD CONSTRAINT FK_Detalle_Caja_Caja
	FOREIGN KEY (cod_caja) REFERENCES t_Caja (cod_caja)
;

ALTER TABLE t_Detalle_Caja ADD CONSTRAINT FK_t_Detalle_Caja_t_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Filial ADD CONSTRAINT FK_t_filial_t_Distrito
	FOREIGN KEY (iddistrito) REFERENCES t_Distrito (iddistrito)
;

ALTER TABLE t_Caja ADD CONSTRAINT FK_Caja_filial
	FOREIGN KEY (cod_filial) REFERENCES t_Filial (cod_filial)
;

ALTER TABLE t_Persona_Caja ADD CONSTRAINT FK_Persona_Caja_Caja
	FOREIGN KEY (cod_caja) REFERENCES t_Caja (cod_caja)
;

ALTER TABLE t_Persona_Caja ADD CONSTRAINT FK_t_Persona_Caja_t_Persona
	FOREIGN KEY (id_user_pk) REFERENCES t_Persona (id_user_pk)
;

ALTER TABLE t_Cuenta_Acceso ADD CONSTRAINT FK_t_Cuenta_Acceso_t_Persona
	FOREIGN KEY (id_user_pk) REFERENCES t_Persona (id_user_pk)
;

ALTER TABLE t_Persona ADD CONSTRAINT FK_Persona_t_Categoria_Persona
	FOREIGN KEY (idCategoriaPersona) REFERENCES t_Categoria_Persona (idcategoriapersona)
;

ALTER TABLE t_Cuenta_Persona ADD CONSTRAINT FK_Cuenta_Persona_Moneda
	FOREIGN KEY (cod_moneda) REFERENCES t_Moneda (cod_moneda)
;

ALTER TABLE t_Cuenta_Persona ADD CONSTRAINT FK_t_Cuenta_Persona_t_Persona
	FOREIGN KEY (id_user_pk) REFERENCES t_Persona (id_user_pk)
;

ALTER TABLE t_Cuenta_Persona ADD CONSTRAINT FK_Cuenta_Persona_Tipo_Cuenta
	FOREIGN KEY (codigo_cuenta) REFERENCES t_Tipo_Cuenta (codigo_cuenta)
;

INSERT INTO FINANCEBANK.T_TRANSACCION_C (IDTRANSCAPITA, TIPO_OPERACION, FECHA, FECHAULTIMA)
	VALUES (to_char(systimestamp, 'yyyymmddhh24missFF'), 'CAPITALIZACION', to_char(sysdate, 'yyyy/mm/dd'),  to_char(sysdate, 'yyyy/mm/dd'))
