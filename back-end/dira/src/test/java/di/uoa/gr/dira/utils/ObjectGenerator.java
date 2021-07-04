package di.uoa.gr.dira.utils;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.issue.IssueLinkModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.util.LongStringPair;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ObjectGenerator {
    private static boolean initialized = false;
    private static final Map<Class<?>, EasyRandomParameters> typeParameters = new HashMap<>();

    public static <T> T generateObject(Class<T> clazz) {
        return new EasyRandom().nextObject(clazz);
    }

    public static <T> T generateObject(Class<T> clazz, EasyRandomParameters params) {
        return new EasyRandom(params).nextObject(clazz);
    }

    public static <T> T generateObjectWithDefaultTypeParams(Class<T> clazz) {
        return generateObject(clazz, typeParameters.get(clazz));
    }

    public static <T> List<T> generateObjectList(Class<T> clazz, int numberOfObjects) {
        return new EasyRandom().objects(clazz, numberOfObjects).collect(Collectors.toList());
    }

    public static <T> List<T> generateObjectList(Class<T> clazz, EasyRandomParameters params, int numberOfObjects) {
        List<T> res = new ArrayList<>(numberOfObjects);

        for (int i = 0; i != numberOfObjects; ++i) {
            res.add(generateObject(clazz, params));
        }

        return res;
    }

    public static <T> List<T> generateObjectListWithDefaultTypeParams(Class<T> clazz, int numberOfObjets) {
        return generateObjectList(clazz, typeParameters.get(clazz), numberOfObjets);
    }

    public static void initializeDefaultTypeParameters() {
        if (!initialized) {
            initialized = true;
            initializeCustomerParameters();
            initializeProjectParameters();
            initializeIssueParameters();
            initializeIssueModelParameters();
        }
    }

    private static void initializeCustomerParameters() {
        EasyRandomParameters params = new EasyRandomParameters();
        params.setStringLengthRange(new EasyRandomParameters.Range<>(1, 50));
        params.setCollectionSizeRange(new EasyRandomParameters.Range<>(0, 0));
        params.excludeField(field -> field.getAnnotation(Id.class) != null);
        typeParameters.put(Customer.class, params);
    }

    private static void initializeProjectParameters() {
        EasyRandomParameters params = new EasyRandomParameters();
        params.setStringLengthRange(new EasyRandomParameters.Range<>(1, 15));
        params.setCollectionSizeRange(new EasyRandomParameters.Range<>(0, 0));
        params.setDateRange(new EasyRandomParameters.Range<>(LocalDate.parse("2021-01-01"), LocalDate.parse("2021-12-31")));
        params.excludeField(field -> field.getAnnotation(Id.class) != null);
        typeParameters.put(Project.class, params);
    }

    private static void initializeIssueParameters() {
        EasyRandomParameters params = new EasyRandomParameters();
        params.setStringLengthRange(new EasyRandomParameters.Range<>(1, 15));
        params.setCollectionSizeRange(new EasyRandomParameters.Range<>(0, 0));
        params.excludeField(field -> field.getAnnotation(Id.class) != null);
        typeParameters.put(Issue.class, params);
    }

    private static void initializeIssueModelParameters() {
        EasyRandomParameters params = new EasyRandomParameters();
        params.setStringLengthRange(new EasyRandomParameters.Range<>(1, 15));
        params.setCollectionSizeRange(new EasyRandomParameters.Range<>(0, 0));
        params.excludeField(field -> field.getAnnotation(Id.class) != null);
        typeParameters.put(IssueModel.class, params);
    }
}
