package data.mission;

import data.Indexible;
import data.Reference;
import util.Util;

public class Mission extends Indexible{
	String verb;
	Reference local;
	Reference localRef;
	Reference remote;
	Reference remoteRef;
	Reference regional;
	Reference regionalRef;
	String[] object;
	

	public Mission(float... floats) {
		super(floats);
	}
	public Mission(int... index) {
		super(index);
	}
	
	
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
	public Reference getLocal() {
		return local;
	}
	public void setLocal(Reference local) {
		this.local = local;
	}
	public Reference getLocalRef() {
		return localRef;
	}
	public void setLocalRef(Reference localRef) {
		this.localRef = localRef;
	}
	public Reference getRemote() {
		return remote;
	}
	public void setRemote(Reference remote) {
		this.remote = remote;
	}
	public Reference getRemoteRef() {
		return remoteRef;
	}
	public void setRemoteRef(Reference remoteRef) {
		this.remoteRef = remoteRef;
	}
	public Reference getRegional() {
		return regional;
	}
	public void setRegional(Reference regional) {
		this.regional = regional;
	}
	public Reference getRegionalRef() {
		return regionalRef;
	}
	public void setRegionalRef(Reference regionalRef) {
		this.regionalRef = regionalRef;
	}
	public String[] getObject() {
		return object;
	}
	public void setObject(String[] object) {
		this.object = object;
	}
	
	public String toString() {
		StringBuilder c1Text = new StringBuilder();
		c1Text.append(verb).append(" ").append(Util.parseArray(object));
		if(local!=null) c1Text.append("\r\n").append("Local NPC: ").append(local.toString());
		if(localRef!=null) c1Text.append("\r\n").append("    \u21B3Location: ").append(localRef.toString());
		if(remote!=null) c1Text.append("\r\n").append("Remote NPC: ").append(remote.toString());
		if(remoteRef!=null) c1Text.append("\r\n").append("    \u21B3Location: ").append(remoteRef.toString());
		if(regional!=null) c1Text.append("\r\n").append("Regional NPC: ").append(regional.toString());
		if(regionalRef!=null) c1Text.append("\r\n").append("    \u21B3Faction: ").append(regionalRef.toString());
		return c1Text.toString();
	}

}
