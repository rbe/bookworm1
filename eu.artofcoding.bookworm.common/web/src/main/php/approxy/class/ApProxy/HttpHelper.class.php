<?php

namespace ApProxy;

class HttpHelper
{

    private function __construct()
    {

    }

    /**
     * @param $status
     */
    public static function sendHttpRedirectWithStatus($status)
    {
        header('Location: /?status=' . $status);
    }

}
