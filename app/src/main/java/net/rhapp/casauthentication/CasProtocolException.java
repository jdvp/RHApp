package net.rhapp.casauthentication;

/**
 * Created by JD on 1/31/2015.
*/
@SuppressWarnings("serial")
public class CasProtocolException extends Exception
{

    public CasProtocolException ()
    {
        super ();
    }
    public CasProtocolException (String message)
    {
        super (message);
    }
}