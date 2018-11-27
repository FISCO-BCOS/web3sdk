package org.fisco.bcos.web3j.console;

import org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.fisco.bcos.web3j.codegen.TruffleJsonFunctionWrapperGenerator;
import org.fisco.bcos.web3j.utils.Console;
import org.fisco.bcos.web3j.utils.Version;

import static org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator.COMMAND_SOLIDITY;
import static org.fisco.bcos.web3j.utils.Collection.tail;

/**
 * Main entry point for running command line utilities.
 */
public class Runner {

    private static String USAGE = "Usage: web3j version|wallet|solidity ...";

    private static String LOGO = "\n" // generated at http://patorjk.com/software/taag
            + "              _      _____ _     _        \n"
            + "             | |    |____ (_)   (_)       \n"
            + "__      _____| |__      / /_     _   ___  \n"
            + "\\ \\ /\\ / / _ \\ '_ \\     \\ \\ |   | | / _ \\ \n"
            + " \\ V  V /  __/ |_) |.___/ / | _ | || (_) |\n"
            + "  \\_/\\_/ \\___|_.__/ \\____/| |(_)|_| \\___/ \n"
            + "                         _/ |             \n"
            + "                        |__/              \n";

    public static void main(String[] args) throws Exception {
        System.out.println(LOGO);

        if (args.length < 1) {
            Console.exitError(USAGE);
        } else {
            switch (args[0]) {
                case "wallet":
                    WalletRunner.run(tail(args));
                    break;
                case COMMAND_SOLIDITY:
                    SolidityFunctionWrapperGenerator.main(tail(args));
                    break;
                case "truffle":
                    TruffleJsonFunctionWrapperGenerator.run(tail(args));
                    break;
                case "version":
                    Console.exitSuccess("Version: " + Version.getVersion() + "\n"
                            + "Build timestamp: " + Version.getTimestamp());
                    break;
                default:
                    Console.exitError(USAGE);
            }
        }
    }
}
