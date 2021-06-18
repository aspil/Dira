package di.uoa.gr.dira.configuration.modelMapper;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.models.customer.CustomerModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        configureCustomerModelToCustomerEntity(mapper);
    }

    /**
     * Creates a model mapper configuration when mapping CustomerModel to Customer entity
     *
     * @param mapper The mapper to configure
     */
    private void configureCustomerModelToCustomerEntity(ModelMapper mapper) {
        TypeMap<CustomerModel, Customer> typeMap = mapper.createTypeMap(CustomerModel.class, Customer.class);
        typeMap.addMappings(m -> m.skip(Customer::setPassword));
        typeMap.addMappings(m -> m.map(CustomerModel::getPassword, Customer::setFromRawPassword));
        typeMap.addMappings(m -> m.map(CustomerModel::getSubscriptionPlan, Customer::setSubscriptionPlanFromEnum));
    }
}
