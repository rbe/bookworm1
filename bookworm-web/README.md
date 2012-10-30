# bookworm-web

## JavaServer Faces

    <managed-bean>
        <managed-bean-name>bookwormBean</managed-bean-name>
        <managed-bean-class>eu.artofcoding.bookworm.BookwormBean</managed-bean-class>
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
        ServerAdmin webmaster@goa.medienhof.de
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
                # We use www.ventplan.com only
                #RewriteCond %{HTTP_HOST} !^www.ventplan.com$
                #RewriteRule ^(.*)$ http://www.ventplan.com/$1 [L,R=301]
                # Education to be done
                RewriteRule /bookworm-web/(.*) http://127.0.2.3:8081/bookworm-web/$1 [P,L]
                RewriteRule search.xhtml http://127.0.2.3:8081/bookworm-web/search.xhtml [P,L]
                RewriteRule result.xhtml http://127.0.2.3:8081/bookworm-web/result.xhtml [P,L]
                RewriteRule bookdetail.xhtml http://127.0.2.3:8081/bookworm-web/bookdetail.xhtml [P,L]
        </IfModule>
    </VirtualHost>
