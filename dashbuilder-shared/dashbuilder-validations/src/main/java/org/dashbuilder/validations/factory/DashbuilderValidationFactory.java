package org.dashbuilder.validations.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;
import org.dashbuilder.dataset.def.CSVDataSetDef;
import org.dashbuilder.dataset.def.DataSetDef;
import org.dashbuilder.dataset.def.SQLDataSetDef;
import org.dashbuilder.dataset.validation.groups.*;

import javax.validation.Validator;

public class DashbuilderValidationFactory extends AbstractGwtValidatorFactory {

    @GwtValidation(value = SQLDataSetDef.class)
    public interface SQLDataSetDefValidator extends DataSetDefValidator {
    }

    @GwtValidation(value = CSVDataSetDef.class, groups = {CSVDataSetDefFilePathValidation.class, CSVDataSetDefFileURLValidation.class})
    public interface CSVDataSetDefValidator extends DataSetDefValidator {
    }
    
    @GwtValidation(value = DataSetDef.class,  groups = {DataSetDefRefreshIntervalValidation.class, DataSetDefPushSizeValidation.class, DataSetDefCacheRowsValidation.class})
    public interface DataSetDefValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(DataSetDefValidator.class);
    }
}