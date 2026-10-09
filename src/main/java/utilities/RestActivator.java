package utilities;

import ressourcerest.EtudiantRest;
import ressourcerest.OptionRest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("rest")
public class RestActivator extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<>();
		resources.add(OptionRest.class);
		resources.add(EtudiantRest.class);
		return resources;
	}
}