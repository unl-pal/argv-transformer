README
======

Configuración del security domain (realm) en wilfdly 8 

    <security-domain name="jdbc-real">
        <authentication>
            <login-module code="Database" flag="required">
                <module-option name="dsJndiName" value="java:jboss/oneEscrituraPostgreDS"/>
                <module-option name="principalsQuery" value="select pasword from seguridad.usuario_cas where lower(usuario) = lower(?)"/>
                <module-option name="rolesQuery" value="select r.nombre, 'Roles' from seguridad.rol r join seguridad.usuario_modelo_rol umr on (r.id = umr.rol_id) join seguridad.usuario_modelo um on (um.id = umr.usuario_modelo_seguridad_id) join seguridad.usuario_cas uc on (uc.id = um.usuario_cas_id) where lower(uc.usuario) = lower(?) GROUP BY r.id" />
                <module-option name="hashAlgorithm" value="SHA-256"/>
                <module-option name="unauthenticatedIdentity" value="guest"/>
                <module-option name="hashEncoding" value="base64"/>
            </login-module>
        </authentication>
    </security-domain>

Tener en cuenta de ajustar los queries de principalsQuery y rolesQuery para que se adapten al modelo a utilizar.

Para generar passwords válidas para el sistema, utilizar la clase PasswordGenerator.

Para pruebas la clave *123* debe estar de la siguiente forma en la base de datos:

    pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=