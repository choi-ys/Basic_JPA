package basic.ch05_entity_mapping;

import basic.ch05_entity_mapping.domain.entity.EnumTypeField;
import basic.ch05_entity_mapping.domain.entity.ExampleEntity;
import basic.config.EntityManagerGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/** Java객체와 RDB 테이블 매핑에 필요한 어노테이션 속성 예제
 *   - Java 객체 필드와 DB 테이블 컬럼 매핑 예제
 */
public class EX_02_EntityMappingExample {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        String columnAnnotationExampleFieldToString = "최용석";

        BigInteger columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger = BigInteger.valueOf(10L);
        BigDecimal columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal = BigDecimal.valueOf(3.14);

        int columnAnnotationExampleFieldToPrimitiveInteger = 11;
        long columnAnnotationExampleFieldToPrimitiveLong = 13L;

        Integer columnAnnotationExampleFieldToWrapperInteger = Integer.valueOf(12);
        Long columnAnnotationExampleFieldToWrapperLong = Long.valueOf(14L);

        EnumTypeField enumTypeStringTypeField = EnumTypeField.FIRST_ENUM;
        EnumTypeField enumTypeOrdinalTypeField = EnumTypeField.SECOND_ENUM;

        Date temporalAnnotationDateTypeField = new Date();
        Date temporalAnnotationTimeTypeField = new Date();

        Calendar temporalAnnotationTimeStampTypeField = Calendar.getInstance();

        LocalDate localDateTypeField = LocalDate.now();
        LocalDateTime localDateTimeTypeField = LocalDateTime.now();

        String lobAnnotationTypeField = "대용량 Text";

        String ignoredField = "해당 필드는 DB 컬럼에 매핑되지 않습니다.";

        try {
            ExampleEntity exampleEntity = ExampleEntity.builder()
                    .columnAnnotationExampleFieldToString(columnAnnotationExampleFieldToString)
                    .columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger(columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger)
                    .columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal(columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal)
                    .columnAnnotationExampleFieldToPrimitiveInteger(columnAnnotationExampleFieldToPrimitiveInteger)
                    .columnAnnotationExampleFieldToPrimitiveLong(columnAnnotationExampleFieldToPrimitiveLong)
                    .columnAnnotationExampleFieldToWrapperInteger(columnAnnotationExampleFieldToWrapperInteger)
                    .columnAnnotationExampleFieldToWrapperLong(columnAnnotationExampleFieldToWrapperLong)
                    .enumTypeStringTypeField(enumTypeStringTypeField)
                    .enumTypeOrdinalTypeField(enumTypeOrdinalTypeField)
                    .temporalAnnotationDateTypeField(temporalAnnotationDateTypeField)
                    .temporalAnnotationTimeTypeField(temporalAnnotationTimeTypeField)
                    .temporalAnnotationTimeStampTypeField(temporalAnnotationTimeStampTypeField)
                    .localDateTypeField(localDateTypeField)
                    .localDateTimeTypeField(localDateTimeTypeField)
                    .lobAnnotationTypeField(lobAnnotationTypeField)
                    .ignoredField(ignoredField)
                    .build();

            entityManager.persist(exampleEntity);

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        EntityManagerGenerator.closeEntityManagerFactiory();
    }
}