<?php

error_reporting(E_ALL);
ini_set('display_errors', 'On');

//
// Joomla
//

define('_JEXEC', 1);
if (file_exists(__DIR__ . '/defines.php')) {
    include_once __DIR__ . '/defines.php';
}
if (!defined('_JDEFINES')) {
    define('JPATH_BASE', __DIR__);
    require_once JPATH_BASE . '/includes/defines.php';
}
require_once JPATH_BASE . '/includes/framework.php';
$mainframe = JFactory::getApplication('site');
$mainframe->initialise();

/**
 * Get a value for a field from comprofiler's database for an user.
 * @param $user
 * @param $field
 * @return string
 */
function getUserValueFromComprofiler($user, $field)
{
    $db = JFactory::getDBO();
    $query = 'SELECT ' . $field . ' FROM #__comprofiler WHERE user_id=' . $user->id;
    $db->setQuery($query);
    return $db->loadResult();
}

/**
 * Get user's data.
 * @return null|string
 */
function getHoerernummer()
{
    $user = JFactory::getUser();
    if (isset($user)) {
        $hnr = getUserValueFromComprofiler($user, 'cb_hoerernummer');
        if (isset($hnr)) {
            return $hnr;
        }
    } else {
        return null;
    }
}

//
// Proxy
//

include_once 'approxy/autoload.php';
use ApProxy\AppInfo;
use ApProxy\Factory;

/**
 * Redirect if no user is logged in.
 */
function sendHttpRedirectIfNoUser()
{
    $user = JFactory::getUser();
    $userOk = isset($user) && $user->id > 0;
    if ($userOk) {
        return false;
    } else {
        ApProxy\HttpHelper::sendHttpRedirectWithStatus('NO_USER');
        return true;
    }
}

/**
 * Proxy request to application.
 */
function proxyRequestToApp()
{
    Factory::configure(new AppInfo('catalog', 'http://127.0.0.1:8090', '/catalog'));
    Factory::configure(new AppInfo('customer', 'http://127.0.0.1:8080', '/customer'));
    $approxy = Factory::create($_SERVER['REQUEST_URI']);
    if (isset($approxy)) {
        $approxy->perform(function ($app, $request_uri) {
            /*
            $session = JFactory::getSession();
            if (empty($session->get('is_first_visit'))) {
                $hnr = getHoerernummer($user);
                if (isset($hnr)) {
                    $forward_to_resource = $requested_resource . '?hnr=' . $hnr;
                    $session->set('is_first_visit', 'hello');
                } else {
                    echo "Error: no hnr";
                    return;
                }
            } else {
                $forward_to_resource = $requested_resource;
            }
            */
            $hnr = getHoerernummer();
            $uriHasQuery = isset($parsed_uri['query']);
            if ($uriHasQuery) {
                $app_uri = $request_uri . '&hnr=' . $hnr;
            } else {
                $app_uri = $request_uri . '?hnr=' . $hnr;
            }
            return $app_uri;
        });
    } else {
        ApProxy\HttpHelper::sendHttpRedirectWithStatus('NO_APPROXY');
    }
}

sendHttpRedirectIfNoUser() or proxyRequestToApp();

?>
