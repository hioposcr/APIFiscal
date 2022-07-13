package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.Collection;
import java.util.Map;

public class ModifierField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "ModifierField", key = "Key", attribute = true, inline = true)
    private Map<String, String> ModifierFields;

    /*@Element(name = "CustomProductFields",required = false)
    private CustomProductFields customProductFields;*/

    @ElementList(name = "Modifiers", required = false)
    private Collection<ModifierField> Modifiers;

    public Map<String, String> getModifierFields() {
        return ModifierFields;
    }

    public void setModifierFields(Map<String, String> modifierFields) {
        ModifierFields = modifierFields;
    }

    /*public CustomProductFields getCustomProductFields(){
        return customProductFields;
    }

    public void setCustomProductFields(CustomProductFields fields){
        this.customProductFields = fields;
    }*/

    public Collection<ModifierField> getModifiers() {
        return Modifiers;
    }

    public void setModifiers(Collection<ModifierField> modifiers) {
        Modifiers = modifiers;
    }
}
