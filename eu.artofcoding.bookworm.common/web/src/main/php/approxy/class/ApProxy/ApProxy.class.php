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
     * @param $appRequestUri string
     */
    private function requestResponseWithApp($appRequestUri)
    {
        $proxy_destination = $this->appInfo->getProxyDestination();
        $parameterSet = isset($proxy_destination) && isset($appRequestUri);
        if ($parameterSet) {
            $forwardToUrl = $proxy_destination . $appRequestUri;
            $request = Request::createFromGlobals();
            $response = Factory::forward($request)->to($forwardToUrl);
            $response->send();
        } else {
            HttpHelper::sendHttpRedirectWithStatus('NO_APP_REQUEST');
        }
    }

    /**
     * @param $customizeUriDelegate
     */
    public function perform($customizeUriDelegate)
    {
        if (isset($customizeUriDelegate)) {
            $appUri = $customizeUriDelegate($this->appInfo, $this->requestUri);
        }
        if (isset($appUri)) {
            $this->requestResponseWithApp($appUri);
        } else if (isset($requestUri)) {
            $this->requestResponseWithApp($requestUri);
        } else {
            HttpHelper::sendHttpRedirectWithStatus('NO_APP_URI');
        }
    }

}

?>
