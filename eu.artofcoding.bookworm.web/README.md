# bookworm-web

## JavaServer Faces

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

## JBoss

### Datasource Configuration

    <subsystem xmlns="urn:jboss:domain:datasources:1.0">
        <datasources>
            <datasource jta="true" jndi-name="java:jboss/datasources/bookwormDatasource" pool-name="bookwormDatasourcePool" enabled="true" use-java-context="true" use-ccm="true">
                <connection-url>jdbc:mysql://localhost:3306/bookworm</connection-url>
                <driver>com.mysql</driver>
                <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
                <pool>
                    <min-pool-size>10</min-pool-size>
                    <max-pool-size>100</max-pool-size>
                    <prefill>true</prefill>
                </pool>
                <security>
                    <user-name>bookworm</user-name>
                    <password>bookworm</password>
                </security>
                <statement>
                    <prepared-statement-cache-size>32</prepared-statement-cache-size>
                    <share-prepared-statements>true</share-prepared-statements>
                </statement>
            </datasource>
            <drivers>
                <driver name="com.mysql" module="com.mysql">
                    <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                </driver>
            </drivers>
        </datasources>
    </subsystem>

### SMTP Configuration

    <subsystem xmlns="urn:jboss:domain:mail:1.0">
        <mail-session jndi-name="java:/bookworm-smtp">
            <smtp-server ssl="true" outbound-socket-binding-ref="bookworm-smtp">
                <login name="wbh@wbh-online.de" password="xxx"/>
            </smtp-server>
        </mail-session>
    </subsystem>

    <socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
        <outbound-socket-binding name="bookworm-smtp">
            <remote-destination host="mail2.1ci.net" port="25"/>
        </outbound-socket-binding>
    </socket-binding-group>

## Web Server

### Virtual Host Configuration 

    <VirtualHost *:80>
        ServerName www.wbh-online.de
        ServerAlias wbh-online.de
        ServerAdmin webmaster@wbh-online.de
        DocumentRoot /usr/home/cew/apache/sites/wbh-online.de/www/
        <Directory /usr/home/cew/apache/sites/wbh-online.de/www>
            Options Indexes FollowSymLinks MultiViews
            AllowOverride All
            Order allow,deny
            allow from all
        </Directory>
        <IfModule mod_rewrite.c>
            RewriteEngine On
            Options FollowSymLinks
            RewriteRule /bookworm-web/(.*) http://127.0.2.3:8081/bookworm-web/$1 [P,L]
            RewriteRule search.xhtml http://127.0.2.3:8081/bookworm-web/search.xhtml [P,L]
            RewriteRule result.xhtml http://127.0.2.3:8081/bookworm-web/result.xhtml [P,L]
            RewriteRule bookdetail.xhtml http://127.0.2.3:8081/bookworm-web/bookdetail.xhtml [P,L]
        </IfModule>
    </VirtualHost>
