<?php

error_reporting(E_ALL);
ini_set('display_errors', 'On');

//
// Joomla
//

define('JOOMLA_HOME', __DIR__ . '/..');
define('_JEXEC', 1);
if (file_exists(JOOMLA_HOME . '/defines.php')) {
    include_once JOOMLA_HOME . '/defines.php';
}
if (!defined('_JDEFINES')) {
    define('JPATH_BASE', JOOMLA_HOME);
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

include_once 'autoload.php';
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
        $customizeUriDelegate = function ($app, $requestUri) {
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
            $parsedRequestUri = parse_url($requestUri);
            $uriHasQuery = isset($parsedRequestUri['query']);
            if ($uriHasQuery) {
                $appUri = $requestUri . '&hnr=' . $hnr;
            } else {
                $appUri = $requestUri . '?hnr=' . $hnr;
            }
            return $appUri;
        };
        $approxy->perform($customizeUriDelegate);
    } else {
        ApProxy\HttpHelper::sendHttpRedirectWithStatus('NO_APPROXY');
    }
}

sendHttpRedirectIfNoUser() or proxyRequestToApp();

?>
