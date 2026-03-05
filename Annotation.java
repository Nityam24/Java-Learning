public class Annotation {

    // @Retention(RetentionPolicy.RUNTIME)
    // @Target(ElementType.TYPE)

    // @Auditable(level = "DETAILED", includeFields = true)

    // @Entity // tells JPA: map this class to a database table
    // @Table(name = "bank_accounts") // tells JPA: use this specific table name
    // public class BankAccount {

    // @Id // tells JPA: this is the primary key
    // @GeneratedValue // tells JPA: auto-generate the ID value
    // private Long id;

    // @NotNull // tells Bean Validation: this field cannot be null
    // @Column(name = "account_holder") // tells JPA: map to this column name
    // private String accountHolder;

    // @Override // tells compiler: this method overrides a parent method
    // public String toString() {
    // return "BankAccount{id=" + id + "}";
    // }
    // }

    // // 1. Marker annotation — no elements
    // @Override

    // // 2. Single-element annotation — one value, element named "value"
    // @SuppressWarnings("unchecked")

    // // 3. Multi-element annotation — named elements
    // @RequestMapping(path = "/accounts", method = RequestMethod.GET)

    // // 4. Array value
    // @SuppressWarnings({ "unchecked", "deprecation" })

    // // 5. Annotations on different code elements
    // @Service // on a class
    // public class PaymentService {

    // @Autowired // on a field
    // private AccountRepo repo;

    // @Transactional // on a method
    // public void transfer(@RequestParam String id) {
    // } // on a parameter
    // }

    // Custom Annotations
    // //////////////////////////////////////////////////////////////
    public @interface AnnotationName {
        // annotation elements (like abstract methods)
        // ReturnType elementName();

        // ReturnType elementWithDefault() default defaultValue;
    }

}
