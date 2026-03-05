// package: com.company.service

// public class BaseService {

//     protected void validateInput(String input) {
//     }
// }

// // package: com.company.controller (DIFFERENT package)
// public class UserController extends BaseService {

//     void process() {
//         validateInput("test");  // ✅ — subclass access, even in different package

//         BaseService other = new BaseService();
//         other.validateInput("test");  // ❌ — protected is NOT accessible via OTHER object
//         //        only accessible via inheritance (this)
//     }
// }

public class Packages {

    public static void main(String[] args) {

    }
}
