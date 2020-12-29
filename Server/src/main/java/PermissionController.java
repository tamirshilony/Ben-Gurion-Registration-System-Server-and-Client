import java.util.Vector;

public class PermissionController {
    private Database db;
    private boolean isRegistered;
    private boolean isgitAdmin;
    private boolean isLoggedin;


    //message registerUser(opCode)
        //if db.reg(user)
            //change isReg
            //if(op == regAd){isAdmin = true}
            //return MF.createmsg(Acc,regg)
        //else
            //return MF.createmsg(ERR,regg)

    //message login()
        //User user = db.getUser(msg.username)
        //if(user!= null && msg.pass == user.pass)
            //change status to loggin
            //return ack
        //else
            //return err



    //handleMessage
        //get OpcodeType
        //if not register
            //if opcode = register
                //ret registerUser(op)
            //else
                //ret err
        //else if not logged
            //if opcode =loggin
                //ret loggin(user)
            //else
                //ret err
        //else if (!isAdmin & (op = curseStat || studentStat))
            //ret err
        //ret null

}
