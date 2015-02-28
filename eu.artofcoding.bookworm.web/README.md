# bookworm-web

## JavaServer Faces

faces-context.xml

    <managed-bean>
        <managed-bean-name>bookwormBean</managed-bean-name>
        <managed-bean-class>eu.artofcoding.bookworm.web.BookwormBean</managed-bean-class>
        <managed-bean-scope>session</managed-bean-scope>
        <managed-property>
            <property-name>mailName</property-name>
            <value>WBH Online Shop</value>
        </managed-property>
        <managed-property>
            <property-name>mailUser</property-name>
            <value>wbh@wbh-online.de</value>
        </managed-property>
        <managed-property>
            <property-name>mailSubject</property-name>
            <value>Ihre Bestellung bei der WBH</value>
        </managed-property>
    </managed-bean>

## WildFly

### Undertow

AJP socket-binding: create

    /socket-binding-group=standard-sockets/socket-binding=ajp:add(port=8009)

or modify

    /socket-binding-group=standard-sockets/socket-binding=ajp:write-attribute(name=port,value=8009)

Create new AJP connector

    /subsystem=undertow/server=default-server/ajp-listener=myListener:add(socket-binding=ajp, scheme=http, enabled=true)

### Datasource Configuration

    module add --name=com.mysql --resources=/opt/install/mysql-connector-java-5.1.32-bin.jar --dependencies=javax.api,javax.transaction.api
    /subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-class-name=com.mysql.jdbc.Driver)
    /subsystem=datasources/data-source=bookwormDS:add(jndi-name="java:/jdbc/bookworm", enabled=true, use-ccm=false, driver-name=mysql, connection-url=jdbc:mysql://localhost:3306/bookworm, user-name=bookworm, password=bookworm, min-pool-size=5, max-pool-size=15)

### SMTP Configuration

    /socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=bookworm-smtp:add(host=mx.1ci.net,port=465)
    /subsystem=mail/mail-session=bookworm-mail:add(jndi-name="java:/mail/bookworm")
    /subsystem=mail/mail-session=bookworm-mail/server=smtp:add(outbound-socket-binding-ref=bookworm-smtp, ssl=true, username=mail@example.com, password=xxx)

## Web Server

### Virtual Host Configuration 

    <VirtualHost *:80>
        ...
        <IfModule mod_proxy.c>
            ProxyRequests Off
            <Proxy *>
                Order deny,allow
                Allow from all
            </Proxy>
            ProxyPass        /bookworm/      ajp://127.0.0.1:8009/bookworm/ timeout=1800
            ProxyPassReverse /bookworm/      ajp://127.0.0.1:8009/bookworm/ timeout=1800
            ProxyPass        /bookworm-test/ ajp://127.0.0.1:8019/bookworm/ timeout=1800
            ProxyPassReverse /bookworm-test/ ajp://127.0.0.1:8019/bookworm/ timeout=1800
        </IfModule>
        ...
    </VirtualHost>
