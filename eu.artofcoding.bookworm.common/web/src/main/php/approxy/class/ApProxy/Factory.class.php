<?php

namespace ApProxy;

class Factory
{

    /**
     * @var array AppInfo[]
     */
    private static $app_infos = array();

    private function __construct()
    {
    }

    /**
     * @param $request_uri string
     * @return AppInfo|null
     */
    private static function findApp($request_uri)
    {
        $parsed_request_uri = parse_url($request_uri);
        foreach (static::$app_infos as $app) {
            $app_context_path = $app->getContextPath();
            $app_context_path_len = strlen($app_context_path);
            $uriBeginsWithAppContext = substr($parsed_request_uri['path'], 0, $app_context_path_len) == $app_context_path;
            if ($uriBeginsWithAppContext) {
                $found_app = $app;
                break;
            }
        }
        return isset($found_app) ? $found_app : null;
    }

    /**
     * Configure factory: add an AppInfo for creating proxies.
     * @param $app_info AppInfo
     */
    public static function configure($app_info)
    {
        array_push(static::$app_infos, $app_info);
    }

    /**
     * @param $request_uri string
     * @return ApProxy|null
     */
    public static function create($request_uri)
    {
        $allowed_app = static::findApp($request_uri, static::$app_infos);
        if (isset($allowed_app)) {
            return new ApProxy($allowed_app, $request_uri);
        } else {
            return null;
        }
    }

}
