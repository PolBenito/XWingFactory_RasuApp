package net.simplifiedlearning.simplifiedcoding;

public class LoggedUser {
    private static short idDailyUser, idUser, idAssemblyInstructions;
    private static String UserName, password;

    public LoggedUser(short idDailyUser, short idUser, short idAssemblyInstructions) {
        this.idDailyUser = idDailyUser;
        this.idUser = idUser;
        this.idAssemblyInstructions = idAssemblyInstructions;
    }

    public static short getIdDailyUser(){
        return idDailyUser;
    }

    public static short getIdUser() {
        return idUser;
    }

    public static short getIdAssemblyInstructions() {
        return idAssemblyInstructions;
    }
}
