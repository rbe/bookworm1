# bookworm-web

## Web Server

### Virtual Host Configuration 

    <VirtualHost 78.47.242.146:80>
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
        ##ErrorLog /usr/home/cew/apache/log/www.wbh-online.de-error.log
        ##CustomLog /usr/home/cew/apache/log/www.wbh-online.de-access.log combined
        # Logs mit Logrotation t?glich 
        ##ErrorLog  "|/usr/local/sbin/rotatelogs -f /usr/home/cew/apache/log/www.wbh-online.de-error.log-%Y-%m-%d.log 1209600"
        ## CustomLog "|/usr/local/sbin/rotatelogs -f /usr/home/cew/apache/log/www.wbh-online.de-access.log-%Y-%m-%d.log 1209600" combined
    </VirtualHost>
