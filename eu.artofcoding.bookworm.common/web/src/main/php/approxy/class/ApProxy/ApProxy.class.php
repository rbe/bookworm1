<?php

namespace ApProxy;

use Proxy\Factory;
use Symfony\Component\HttpFoundation\Request;

class ApProxy
{

    /**
     * @var AppInfo
     */
    protected $appInfo;

    /**
     * @var string
     */
    protected $requestUri;

    /**
     * @param $appInfo AppInfo
     * @param $requestUri string
     */
    public function __construct($appInfo, $requestUri)
    {
        $this->appInfo = $appInfo;
        $this->requestUri = $requestUri;
    }

    /**
     * @param $app_request_uri string
     */
    private function requestResponseWithApp($app_request_uri)
    {
        $proxy_destination = $this->appInfo->getProxyDestination();
        $parameterSet = isset($proxy_destination) && isset($app_request_uri);
        if ($parameterSet) {
            $forward_to_url = $proxy_destination . $app_request_uri;
            $request = Request::createFromGlobals();
            $response = Factory::forward($request)->to($forward_to_url);
            $response->send();
        } else {
            HttpHelper::sendHttpRedirectWithStatus('NO_APP_REQUEST');
        }
    }

    /**
     * @param $customize_uri_delegate
     */
    public function perform($customize_uri_delegate)
    {
        if (isset($customize_uri_delegate)) {
            $app_uri = $customize_uri_delegate($this->appInfo, $this->requestUri);
        }
        if (isset($app_uri)) {
            $this->requestResponseWithApp($app_uri);
        } else if (isset($request_uri)) {
            $this->requestResponseWithApp($request_uri);
        } else {
            HttpHelper::sendHttpRedirectWithStatus('NO_APP_URI');
        }
    }

}

?>
