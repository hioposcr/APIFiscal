package fiscalapi.icg.es.apifiscal.Data.HIOPOSCloud.Models.Document;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Version;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class LineField {
    @Version(revision=1.1)
    private double version;

    @ElementMap(entry = "LineField", key = "Key", attribute = true, inline = true)
    private Map<String, String> LineFields;

    @ElementList(name = "Modifiers", required = false)
    private Collection<ModifierField> Modifiers;

    @ElementList(name = "LineTaxes", required = false)
    private Collection<LineTaxField> LineTaxes;

    public Map<String, String> getLineFields() {
        return LineFields;
    }

    @Element(name = "CustomDocLineFields",required = false)
    private CustomDocLineFields customDocLineFields;

    @Element(name = "CustomDocLineSummaryFields",required = false)
    private CustomDocLineSummaryFields customDocLineSummaryFields;

    @ElementList(entry = "CustomProductFields",inline=true,required = false)
    private Collection<CustomProductFields> customProductFields;

    public void setLineFields(Map<String, String> lineFields) {
        LineFields = lineFields;
    }

    public Collection<ModifierField> getModifiers() {
        return Modifiers;
    }

    public void setModifiers(Collection<ModifierField> modifiers) {
        Modifiers = modifiers;
    }

    public Collection<LineTaxField> getLineTaxes() {
        return LineTaxes;
    }

    public void setLineTaxes(Collection<LineTaxField> lineTaxes) {
        LineTaxes = lineTaxes;
    }

    public CustomDocLineFields  getCustomDocLineFields() {
        return customDocLineFields;
    }

    public void setCustomDocLineFields(CustomDocLineFields field) {
        this.customDocLineFields = field;
    }

    public CustomDocLineSummaryFields  getCustomDocLineSummaryFields() {
        return customDocLineSummaryFields;
    }

    public void setCustomDocLineSummaryFields(CustomDocLineSummaryFields field) {
        this.customDocLineSummaryFields = field;
    }

    public ArrayList<CustomProductFields> getCustomProductFields() {
        return (ArrayList<CustomProductFields>)customProductFields;
    }

    public void setCustomProductFields(ArrayList<CustomProductFields> field) {
        this.customProductFields = field;
    }
}
