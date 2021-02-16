package basic.ch05_entity_mapping.domain.entity;

import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/** @Entity : JPA에서 관리하는 엔티티 클래스 지정 시 사용
 * - name : JPA에서 사용할 엔티티 이름, 주로 Class이름이 중복일 경우 이를 구분하기 위해서 사용하며 기본값 사용 권장
 *   - 기본값 : 클래스 이름 그대로 사용
 */
@Entity(name = "ExampleEntity")

/** @Table : 엔티티가 매핑될 테이블 지정, 생략시 @Entity의 name속성 값의 테이블과 매핑
 * - name : 매핑할 DB Table 이름
 * - uniqueConstraints : DDL 생성 시에 unique constraints를 생성, 2개 이상의 복합 unique constraints도 가능
 * - catalog : 매핑할 DB catalog 이름
 * - schema : 매핑할 DB schema 이름
 */
@Table(
        name = "example_tb"
        /**
         * alter table example_tb
         *     add constraint ID_USERNAME_UNIQUE unique (idField, username)
        */
        , uniqueConstraints = {
                @UniqueConstraint(name = "ID_USERNAME_UNIQUE", columnNames = {"idField","username"})
        }
//        , catalog = "example_catalog"
//        , schema = "example_schema"
)
public class ExampleEntity {

    /** @Id : 기본 키 매핑 시 사용, 해당 컬럼이 Primary key 컬럼으로 사용됨을 명시 */
    @Id
    /** @GeneratedValue : Primary key 키의 값을 위한 자동 생성 전략을 명시하는데 사용
     *  - IDENTITY
     *  - SEQUENCE
     *  - TABLE
     *  - AUTO : DB dialect에 맞춰 IDENTITY, SEQUENCE, TABLE중 하나의 옵션이 선택됨
     */
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long idField;

    /** @Column : 필드와 DB컬럼 매핑 시 사용
     * - name : 필드와 매핑할 테이블의 컬럼 이름
     * - nullable(DDL) : null 값 허용 여부 설정, false인 경우 DDL 생성 시 NOT NULL 제약조건 생성 (기본값 : true)
     * - length : 문자 길이 제약 조건 설정 (String 필드에만 적용 가능)
     * - columnDefinition : 컬럼에 적용할 속성을 직접 명시
     * - insertable : 엔티티 저장 시점에 해당 속성이 false인 경우 DB에 저장하지 않으며, 읽기전용 필드에 false 적용 (기본값 : true)
     * - updatable : 엔티티 수정 시점에 해당 속성이 false인 경우 DB에 갱신하지 않으며, 읽기전용 필드에 false 적용 (기본값 : true)
     * - unique(DDL) : 유니크 제약 조건 설정
     *   - 제약조건의 식별자 ID가 랜덤으로 생성되므로 @Table.uniqueConstraints 사용을 권장
     * - precision, scale(DDL) : BigDecimal/BigInteger 타입에 사용
     *   - precision : 소수점을 포함한 전체 자리수를 명시
     *   - scale : 소수점 자릿수를 명시
     *   - double/float 타입에는 적용 되지 않음
     */
    @Column(
            name = "username"
            , nullable = false
            , length = 20
//            , columnDefinition = "varchar(100) default 'EMPTY' comment '컬럼 설명'"
            , insertable = false
            , updatable = false)
    private String columnAnnotationExampleFieldToString;

    /** @Column -> precision, scale 속성 : 정밀한 소수 데이터를 다루는 필드와 DB 컬럼 매핑 시 사용
     * - precision : 소수점을 포함한 전체 자리수를 명시
     * - scale : 소수점 자릿수를 명시
     * 주의 사항
     *  - Double, float 타입에는 미 적용
     */
    @Column(precision = 5, scale = 3)
    private BigInteger columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger;
    private BigDecimal columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal;

    // Primitive 타입 필드에는 NOT NULL 제약 조건이 적용
    private int columnAnnotationExampleFieldToPrimitiveInteger;
    private long columnAnnotationExampleFieldToPrimitiveLong;

    // Wrapper Class 타입 필드에는 NULL 허용
    private Long columnAnnotationExampleFieldToWrapperLong;
    private Integer columnAnnotationExampleFieldToWrapperInteger;

    /** @Enumerated : Enum 타입의 필드와 DB컬럼 매핑 시 사용
     *  - @Enumerated(EnumType.STRING) : Enum의 Value를 DB에 저장
     *  - @Enumerated(EnumType.ORDINAL) : Enum의 순서를 DB에 저장
     * 주의사항 :
     *  - @Enumerated 어노테이션 사용시 기본값인 EnumType.ORDINAL 속성 적용
     *  - EnumType.ORDINAL 속성 적용 시 EnumClass에 명시한 필드의 순서가 달라지게 되는 경우
     *    DB 컬럼에 저장되는 순서값이 명시된 필드가 의미하는 값과 달라게 되므로 사용을 권장하지 않음
     */
    @Enumerated(EnumType.STRING)
    private EnumTypeField enumTypeStringTypeField;

    @Enumerated(EnumType.ORDINAL)
    private EnumTypeField enumTypeOrdinalTypeField;

    /** @Temporal :java.util.Date, java.util.Calendar 타입의 필드와 DB컬럼 매핑 시 사용
     * - @Temporal(TemporalType.DATE) : 날짜 필드를 DB의 date 타입과 매핑 (EX:YYYY-MM-DD)
     * - @Temporal(TemporalType.TIME) : 시간 필드를 DB의 time 타입과 매핑 (EX: hh:mm:ss)
     * - @Temporal(TemporalType.TIMESTAMP) : 날짜와 시간을 포함하는 필드를 DB의 timestamp 타입과 매핑 (EX: YYYY-MM-DD hh:mm:ss)
     * 참고 : java8의 LocalDate, LocalDateTime을 사용할 때는 해당 어노테이션을 생략가능 (최신 하이버네이트 지원)
     */
    @Temporal(TemporalType.DATE)
    private Date temporalAnnotationDateTypeField;

    @Temporal(TemporalType.TIME)
    private Date temporalAnnotationTimeTypeField;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar temporalAnnotationTimeStampTypeField;

    private LocalDate localDateTypeField;
    private LocalDateTime localDateTimeTypeField;

    /** @Lob : 필드와 BLOB, CLOB 타입의 DB컬럼 매핑 시 사용
     * - CLOB : String, char[], java.sql.CLOB
     * - BLOB : byte[], java.sql.BLOB
     * 참고 :
     *  - 매핑하는 필드 타입이 문자면 CLOB 매핑, 그 외는 BLOB 매핑
     *  - @Lob 어노테이션에는 속성 없음
     */
    @Lob
    private String lobAnnotationTypeField;

    /** @Transient : DB와의 매핑에서 제외할 필드에 명시
     * - 해당 어노테이션이 적용된 필드는 DB 저장/조회 시 제외, 임시로 값을 보관할 때 사용
     */
    @Transient
    private String ignoredField;

    public ExampleEntity() {
    }

    @Builder
    public ExampleEntity(
                         String columnAnnotationExampleFieldToString,
                         BigInteger columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger,
                         BigDecimal columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal,
                         int columnAnnotationExampleFieldToPrimitiveInteger,
                         Integer columnAnnotationExampleFieldToWrapperInteger,
                         long columnAnnotationExampleFieldToPrimitiveLong,
                         Long columnAnnotationExampleFieldToWrapperLong,
                         EnumTypeField enumTypeStringTypeField,
                         EnumTypeField enumTypeOrdinalTypeField,
                         Date temporalAnnotationDateTypeField,
                         Date temporalAnnotationTimeTypeField,
                         Calendar temporalAnnotationTimeStampTypeField,
                         LocalDate localDateTypeField,
                         LocalDateTime localDateTimeTypeField,
                         String lobAnnotationTypeField,
                         String ignoredField) {
        this.columnAnnotationExampleFieldToString = columnAnnotationExampleFieldToString;
        this.columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger = columnAnnotationPrecisionAndScalePropertyExampleFieldToBigInteger;
        this.columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal = columnAnnotationPrecisionAndScalePropertyExampleFieldToBigDecimal;
        this.columnAnnotationExampleFieldToPrimitiveInteger = columnAnnotationExampleFieldToPrimitiveInteger;
        this.columnAnnotationExampleFieldToPrimitiveLong = columnAnnotationExampleFieldToPrimitiveLong;
        this.columnAnnotationExampleFieldToWrapperInteger = columnAnnotationExampleFieldToWrapperInteger;
        this.columnAnnotationExampleFieldToWrapperLong = columnAnnotationExampleFieldToWrapperLong;
        this.enumTypeStringTypeField = enumTypeStringTypeField;
        this.enumTypeOrdinalTypeField = enumTypeOrdinalTypeField;
        this.temporalAnnotationDateTypeField = temporalAnnotationDateTypeField;
        this.temporalAnnotationTimeTypeField = temporalAnnotationTimeTypeField;
        this.temporalAnnotationTimeStampTypeField = temporalAnnotationTimeStampTypeField;
        this.localDateTypeField = localDateTypeField;
        this.localDateTimeTypeField = localDateTimeTypeField;
        this.lobAnnotationTypeField = lobAnnotationTypeField;
        this.ignoredField = ignoredField;
    }
}