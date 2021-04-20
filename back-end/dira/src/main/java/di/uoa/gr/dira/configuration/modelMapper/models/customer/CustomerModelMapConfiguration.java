package di.uoa.gr.dira.configuration.modelMapper.models.customer;

import di.uoa.gr.dira.configuration.modelMapper.IMapConfiguration;
import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.models.customer.CustomerModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class CustomerModelMapConfiguration implements IMapConfiguration {
    @Override
    public void configure(ModelMapper mapper) {
        TypeMap<CustomerModel, Customer> typeMap = mapper.createTypeMap(CustomerModel.class, Customer.class);
        typeMap.addMappings(m -> m.map(CustomerModel::getSubscriptionPlan, Customer::setSubscriptionPlanFromEnum));
    }
}
