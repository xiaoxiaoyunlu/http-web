package com.zj.server.common;


public class IpPortPair
    implements Comparable<Object>
{

    public IpPortPair()
    {
        ip = null;
        port = 0;
    }

    public IpPortPair(String ip, int port)
    {
        this.ip = null;
        this.port = 0;
        this.ip = ip;
        this.port = port;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int hashCode()
    {
        int prime = 31;
        int result = 1;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + port;
        return result;
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        IpPortPair other = (IpPortPair)obj;
        if(ip == null)
        {
            if(other.ip != null)
                return false;
        } else
        if(!ip.equals(other.ip))
            return false;
        return port == other.port;
    }

    public String toString()
    {
        return (new StringBuilder()).append(ip).append(":").append(port).toString();
    }

    

    private String ip;
    private int port;
    
	@Override
	public int compareTo(Object o) {
		IpPortPair ipPortPair= (IpPortPair)o;
		int result = ip.compareTo(ipPortPair.ip);
		if(0 == result){
			return port - ipPortPair.port;
		}else{
			return result;
		}
	}
}

