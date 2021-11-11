package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private AccountService accountService;
    private TransferService transferService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL), new TransferService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService, TransferService transferService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;
		this.transferService = transferService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		String token = currentUser.getToken();
		BigDecimal balance = accountService.getBalance(token);
		System.out.println("Your current account balance is: " + balance);
	}

	private void viewTransferHistory() {
		System.out.println("--------------------------------------------");
		System.out.printf(("%-15s %-15s %-15s%n"), "Transfers ID", "From/To", "Amount");
		System.out.println("--------------------------------------------");

		String token = currentUser.getToken();
		Transfer[] transfers = transferService.getAllTransfers(token);
		List<Integer> transferList = new ArrayList<>();
		String fromTo = null;

		for (Transfer transfer : transfers) {
			transferList.add(transfer.getTransferId());

			if (transfer.getAccountFrom() == currentUser.getUser().getId()) {
				fromTo = "To:" + transfer.getAccountFrom();
			}
			if (transfer.getAccountTo() == currentUser.getUser().getId()) {
				fromTo = "From:" + transfer.getAccountTo();
			}

			System.out.printf(("%-15s %-15s %-15s%n"), transfer.getTransferId(), fromTo, transfer.getAmount());
		}

		System.out.println();

		String transferId = console.getUserInput("Please enter transfer ID to view details (0 to cancel)");

		int parseTransferId = Integer.parseInt(transferId);

		if (parseTransferId == 0) {
			mainMenu();
		} else {
			while (!transferList.contains(parseTransferId)) {
				if (parseTransferId == 0) {
					mainMenu();
				} else {
					transferId = console.getUserInput("This ID does not exist. Please try again. Enter ID of transfer you are trying to view (0 to cancel)");
					parseTransferId = Integer.parseInt(transferId);
				}
			}
		}

		Transfer transfer = transferService.getTransfer(parseTransferId, token);

		//System.out.println(transfer);

		System.out.println("--------------------------------------------");
		System.out.println("Transfer Details");
		System.out.println("--------------------------------------------");
		System.out.println("Id: x" + transfer.getTransferId());
		System.out.println("From: x" + transfer.getAccountFrom());
		System.out.println("To: x" + transfer.getAccountTo());
		System.out.println("Type: x" + transfer.getTransferTypeId());
		System.out.println("Status: x" + transfer.getTransferStatusId());
		System.out.println("Amount: $x" + transfer.getAmount());
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		System.out.println("This portion is under maintenance. Please come back and try again.");
	}

	private void sendBucks() {
		System.out.println("--------------------------------------------");
		System.out.printf(("%-15s %-15s%n"), "Users ID", "Name");
		System.out.println("--------------------------------------------");

		String token = currentUser.getToken();
		User[] users = transferService.getAllUsers(token);
		List<Integer> userList = new ArrayList<>();

		for (User user : users) {
			userList.add(user.getId());
			System.out.printf(("%-15s %-15s%n"), user.getId(), user.getUsername());
		}

		System.out.println();

		int parseUserId = 1;

		while(parseUserId < 1000) {
			String userId = console.getUserInput("Enter ID of user you are sending to (0 to cancel)");

			try {
				parseUserId = Integer.parseInt(userId);

				if (parseUserId == 0) {
					mainMenu();
				}

				if (!userList.contains(parseUserId)) {
					System.out.println("This ID does not exist. Please try use an ID from the list.");
					parseUserId = 1;
				}

			} catch (NumberFormatException e) {
				System.out.println("This is not a valid ID. Please try using numbers.");
			}
		}

		BigDecimal parseAmount = new BigDecimal(0);

		while (parseAmount.compareTo(BigDecimal.ZERO) == 0) {
			String userAmount = console.getUserInput("Enter Amount");

			try {
				parseAmount = new BigDecimal(userAmount);

				if (parseAmount.compareTo(BigDecimal.ZERO) < 0) {
					System.out.println("Please enter a positive amount. Please try again.");
					parseAmount = new BigDecimal(0);
				}
			} catch  (Exception w) {
				System.out.println("This is not a valid amount. Please try using numbers.");
			}
		}

		Transfer userTransferObject = transferService.makeTransferObject(parseUserId, parseAmount);

		transferService.createTransfer(userTransferObject, token);
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		System.out.println("This portion is under maintenance. Please come back and try again.");
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}

}
