/**
 *
 */

import org.crsh.cli.Argument;
import org.crsh.cli.Command;
import org.crsh.cli.Man;
import org.crsh.cli.Named;
import org.crsh.cli.Required;
import org.crsh.cli.Usage;

import com.github.gm.hotconf.HotConfigurableProperties;

/**
 * @author Gwendal Mousset
 *
 */
@Usage("change internal app configuration")
class hotconf {
	
	@Usage("list all configurable properties")
	@Command
	@Named("list")
	public void list() {
		Set<String> props = this.getConfigurableProperties().getAllProperties()
		for (prop in props) {
			out.println prop
		}
	}
	
	@Usage("print property value")
	@Command
	@Named("get")
	public Object getPropertyValue(@Argument(name = "property name") @Required String pName) {
		return this.getConfigurableProperties().getPropertyValue(pName)
	}
	
	@Usage("change property value")
	@Command
	@Named("set")
	public Object setPropertyValue(@Argument(name = "property name") @Required String pName, @Argument(name = "property value") @Required String pValue) {
		this.getConfigurableProperties().setPropertyValue(pName, pValue)
	}
	
	/**
	 * @return The ConfigurableProperties Spring bean.
	 */
	private HotConfigurableProperties getConfigurableProperties() {
		return context.attributes.beans["configurableProperties"]
	}
}
