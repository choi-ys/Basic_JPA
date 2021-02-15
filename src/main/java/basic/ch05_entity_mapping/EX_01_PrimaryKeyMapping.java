package basic.ch05_entity_mapping;

import basic.ch05_entity_mapping.domain.entity.GeneratedValueIdentityExampleEntity;
import basic.ch05_entity_mapping.domain.entity.GeneratedValueSequenceExampleEntity;
import basic.config.EntityManagerGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * H2 DB기준 mapping된 java 필드와 매핑된 db 기본키 컬럼 값 자동 생성 예제
 *  - sequence
 *  - identity
 */
public class EX_01_PrimaryKeyMapping {

    public static void main(String[] args) {
        EntityManager entityManager = EntityManagerGenerator.generateEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        String sequenceValue = "sequence를 이용한 기본 키 컬럼 값 자동 생성 예제";
        String identityValue = "identity를 이용한 기본 키 컬럼 값 자동 생성 예제";

        try {
            /** sequence를 이용한 기본 키 컬럼 값 자동 생성 예제 */
            GeneratedValueSequenceExampleEntity sequenceExampleEntity = new GeneratedValueSequenceExampleEntity();
            sequenceExampleEntity.setValue(sequenceValue);
            entityManager.persist(sequenceExampleEntity);

            /** identity를 이용한 기본 키 컬럼 값 자동 생성 예제 */
            GeneratedValueIdentityExampleEntity identityExampleEntity = new GeneratedValueIdentityExampleEntity();
            identityExampleEntity.setValue(identityValue);
            entityManager.persist(identityExampleEntity);

            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

        EntityManagerGenerator.closeEntityManagerFactory();
    }
}