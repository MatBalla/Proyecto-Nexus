--Datos de ejemplo

insert into usuario (usu_id, usu_nombre, usu_correo, usu_clave, usu_rol)
values (nextval('seq_usuario'), 'Administrador', 'admin@uce.edu.ec', 'admin123', 'ADMIN');

insert into usuario (usu_id, usu_nombre, usu_correo, usu_clave, usu_rol)
values (nextval('seq_usuario'), 'Usuario', 'usuario@uce.edu.ec', 'usuario123', 'USUARIO');


insert into agente (age_id, age_nombre, age_instrucciones,
                    age_maximo_tokens, age_usa_documentos, age_activo)
values (nextval('seq_agente'), 'Clasificador',
        'Lee la solicitud y di de que tema trata en una sola palabra.',
        500, false, true);

insert into agente (age_id, age_nombre, age_instrucciones,
                    age_maximo_tokens, age_usa_documentos, age_activo)
values (nextval('seq_agente'), 'Redactor',
        'Escribe una respuesta clara y corta para la solicitud recibida.',
        1000, true, true);

insert into agente (age_id, age_nombre, age_instrucciones,
                    age_maximo_tokens, age_usa_documentos, age_activo)
values (nextval('seq_agente'), 'Revisor',
        'Revisa el texto recibido y corrige los errores que encuentres.',
        800, false, true);
