package net.rhapp.casauthentication;

/**
 * Exception for an anomaly in the CAS protocol
*/
public class CasProtocolException extends Exception
{

//    public CasProtocolException ()
//    {
//        super ();
//    }
    public CasProtocolException (String message)
    {
        super (message);
    }
}