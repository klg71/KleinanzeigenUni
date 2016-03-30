package backend.commands;

import java.io.IOException;
import java.util.ArrayList;

import backend.CategoryManager;
import backend.Command;
import backend.Crypt;
import backend.LoginManager;
import backend.Offer;
import backend.OfferManager;

public class EditUserCommand extends Command {
	private String oldFirstName;
	private String oldLastName;
	private String oldTelefon;
	private String oldAddress;
	private String oldPass;

	public EditUserCommand(LoginManager loginManager,
			OfferManager offerManager, CategoryManager categoryManager) {
		super(loginManager, offerManager, categoryManager);
		keywords.add("edituser");
		keywords.add("Edituser");
		keywords.add("EDITUSER");
	}

	@Override
	public void execute(ArrayList<String> parameters) {
		super.execute(parameters);
		Integer OfferId = 0;
		String firstname = "";
		String lastname = "";
		String telefon = "";
		String address = "";
		String password = "";

		switch (parameters.size()) {
		case 0:
			do {
				System.out.println("Enter new Firstname:("
						+ currentUser.getFirstName() + ")");
				try {
					firstname = br.readLine();
					parameters.add(firstname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (firstname.equals(""));

		case 1:
			if (parameters.size() == 1) {
				firstname = parameters.get(0);
			}
			if (firstname.equals("")) {
				System.out.println("Please enter valid information!");
				return;
			}

			do {
				System.out.println("Enter new Lastname("
						+ currentUser.getLastName() + "):");
				try {
					lastname = br.readLine();
					parameters.add(lastname);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (firstname.equals(""));
		case 2:
			if (parameters.size() == 2) {
				lastname = parameters.get(0);
				firstname = parameters.get(1);
			}
			if (lastname.equals("") ||firstname.equals("")) {
				System.out.println("Please enter valid information!");
				return;
			}

			do {
				System.out.println("Enter new address("
						+ currentUser.getAddress() + "):");
				try {
					address = br.readLine();
					parameters.add(address);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (firstname.equals(""));
		case 3:
			if (parameters.size() == 3) {
				lastname = parameters.get(0);
				firstname = parameters.get(1);
				address = parameters.get(2);
			}
			if (lastname.equals("") ||firstname.equals("")
					||address.equals("")) {
				System.out.println("Please enter valid information!");
				return;
			}

			do {
				System.out.println("Enter new telefon("
						+ currentUser.getTelefon() + "):");
				try {
					telefon = br.readLine();
					parameters.add(telefon);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (telefon.equals(""));

		case 4:
			if (parameters.size() == 3) {
				lastname = parameters.get(0);
				firstname = parameters.get(1);
				address = parameters.get(2);
				telefon = parameters.get(3);
			}
			if (lastname.equals("") ||firstname.equals("")
					||address.equals("") ||telefon.equals("")) {
				System.out.println("Please enter valid information!");
				return;
			}

			do {
				System.out.println("Enter new password:");
				try {
					password = br.readLine();
					parameters.add("****");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (password.equals(""));
			break;

		default:
			if (parameters.size() > 4) {
				lastname = parameters.get(0);
				firstname = parameters.get(1);
				address = parameters.get(2);
				telefon = parameters.get(3);
				password = parameters.get(4);
				if (lastname.equals("") ||firstname.equals("")
						||address.equals("") ||telefon.equals("")
						||password.equals("")) {
					System.out.println("Please enter valid information!");
					return;
				}
			}
		}
		oldAddress=currentUser.getAddress();
		oldFirstName=currentUser.getFirstName();
		oldLastName=currentUser.getLastName();
		oldTelefon=currentUser.getTelefon();
		oldPass=currentUser.getPasswordHash();
		
		currentUser.setAddress(address);
		currentUser.setFirstName(firstname);
		currentUser.setLastName(lastname);
		currentUser.setTelefon(telefon);
		currentUser.setPasswordHash(Crypt.getSHA1(password));
		loginManager.editUser(currentUser);
		isDone=true;
		System.out.println("Edited User!");

	}

	@Override
	public void undo() {
		if(isDone){
			currentUser.setAddress(oldAddress);
			currentUser.setFirstName(oldFirstName);
			currentUser.setLastName(oldLastName);
			currentUser.setTelefon(oldTelefon);
			currentUser.setPasswordHash(oldPass);
			loginManager.editUser(currentUser);
			System.out.println("ReEdited User!");
		}
		
	}

}
