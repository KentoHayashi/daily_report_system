package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import utils.DButil;

public class EmployeeValidator {
    public static List<String> validate(Employee e, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        String code_error = validateCode(e.getCode(), codeDuplicateCheckFlag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        String name_error = validateName(e.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        String password_error = validatePassword(e.getPassword(), passwordCheckFlag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        return errors;

    }

    /*社員番号*/
    private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
        if(code == null || code.equals("")) {
            return "社員番号を入力して下さい。";
        }

        if(codeDuplicateCheckFlag) {
            EntityManager em = DButil.createEntityManager();
            long employee_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class).setParameter("code", code).getSingleResult();
            em.close();
            if(employee_count > 0) {
                return "入力された社員番号はすでに存在しています。";
            }
        }

        return "";
    }

    /*社員名の必須入力チェック*/
    private static String validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力して下さい。";
        }

        return "";

    }

    /*パスワードの必須入力チェック*/
    private static String validatePassword(String password, Boolean passwordCheckFlag) {
        if(passwordCheckFlag && (password == null || password.equals(""))) {
            return "パスワードを入力して下さい。";
        }

        return "";

    }


}
