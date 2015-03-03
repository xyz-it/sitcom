package com.xyzit.sitcom.com.xyzit.sitcom.util.http;

/**
 * Created by kimveasna on 03/03/2015.
 */
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class SimpleSoapSerializationEnveloppe extends SoapSerializationEnvelope {

    public SimpleSoapSerializationEnveloppe(int version) {
        super(version);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.ksoap2.serialization.SoapSerializationEnvelope#getResponse()
     */
    @Override
    public Object getResponse() throws SoapFault {
        // TODO Auto-generated method stub
        //return super.getResponse();
//		if (bodyIn instanceof SoapFault)
//		{
//			throw (SoapFault) bodyIn;
//		}
//		KvmSerializable ks = (KvmSerializable) bodyIn;
//		return ks.getPropertyCount() == 0 ? null : ks;
        return bodyIn;
    }

}