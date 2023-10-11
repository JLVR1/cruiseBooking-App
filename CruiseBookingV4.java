import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CruiseBookingV4 {
    private JFrame frame;
    private JPanel cardPanel;
    private JPanel cruiseSelectionPanel;
    private JPanel userInformationPanel;
    private JPanel cardPaymentPanel;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JTextField countryOfResidenceField;
    private JTextField emailAddressField;
    private JTextField phoneNumberField;
    private JTextField cardHolder;
    private JTextField cardNumber;
    private JTextField expiryDate;
    private JTextField cvv;
    private JComboBox<String> cruiseComboBox;
    private JComboBox<String> genderComboBox;
    private JCheckBox disabilityCheckBox;

    private String selectedCruise;
    private String selectedSuite;
    private int cruisePrice;
    private int suitePrice;
    private int totalPrice;

    public CruiseBookingV4() {
        frame = new JFrame("Cruise Booking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 550);

        cardPanel = new JPanel(new CardLayout());

        createCruiseSelectionPanel();
        createUserInfoPanel();
        createCardPaymentPanel();

        frame.getContentPane().add(cardPanel);
        frame.setVisible(true);
    }

    private void createCruiseSelectionPanel() {
        cruiseSelectionPanel = new JPanel(new GridBagLayout());
        cruiseSelectionPanel.setBorder(BorderFactory.createTitledBorder("Cruise Selection"));
        GridBagConstraints cruiseConstraints = new GridBagConstraints();
        cruiseConstraints.fill = GridBagConstraints.HORIZONTAL;
        cruiseConstraints.insets = new Insets(10, 10, 10, 10);

        String[] cruiseOptions = {
            "Cruise A (R18,000)",
            "Cruise B (R28,000)",
            "Cruise C (R37,000)"
        };

        cruiseComboBox = new JComboBox<>(cruiseOptions);
        addLabel(cruiseSelectionPanel, "Select a Cruise:", cruiseConstraints, 0);
        cruiseConstraints.gridx = 1;
        cruiseSelectionPanel.add(cruiseComboBox, cruiseConstraints);

        addLabel(cruiseSelectionPanel, "Select Suite Type:", cruiseConstraints, 1);

        JRadioButton standardSuite = new JRadioButton("Standard Suite (R9,500)");
        JRadioButton deluxeSuite = new JRadioButton("Deluxe Suite (R15,000)");
        JRadioButton premiumSuite = new JRadioButton("Premium Suite (R22,000)");

        ButtonGroup suiteGroup = new ButtonGroup();
        suiteGroup.add(standardSuite);
        suiteGroup.add(deluxeSuite);
        suiteGroup.add(premiumSuite);

        JPanel suitePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        suitePanel.add(standardSuite);
        suitePanel.add(deluxeSuite);
        suitePanel.add(premiumSuite);

        cruiseConstraints.gridx = 0;
        cruiseConstraints.gridy = 2;
        cruiseConstraints.gridwidth = 2;
        cruiseSelectionPanel.add(suitePanel, cruiseConstraints);

        JButton nextPageButton1 = createStyledButton("Next");
        nextPageButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCruise = (String) cruiseComboBox.getSelectedItem();
                selectedSuite = getSelectedSuite(standardSuite, deluxeSuite, premiumSuite);

                cruisePrice = extractPrice(selectedCruise);
                suitePrice = extractPrice(selectedSuite);
                totalPrice = cruisePrice + suitePrice;

                JOptionPane.showMessageDialog(frame, "Total Price: R" + totalPrice);

                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "userInformation");
            }
        });
        cruiseConstraints.gridx = 0;
        cruiseConstraints.gridy = 3;
        cruiseConstraints.gridwidth = 2;
        cruiseSelectionPanel.add(nextPageButton1, cruiseConstraints);
        cardPanel.add(cruiseSelectionPanel, "cruiseSelection");
    }

    private void createUserInfoPanel() {
        userInformationPanel = new JPanel(new GridBagLayout());
        userInformationPanel.setBorder(BorderFactory.createTitledBorder("User Information"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        addLabel(userInformationPanel, "Welcome user, please enter your details below", constraints, 0);

        firstNameField = addTextField(userInformationPanel, "First Name:", constraints, 1);
        lastNameField = addTextField(userInformationPanel, "Last Name:", constraints, 2);

        String[] genderOptions = { "Male", "Female", "Other" };
        genderComboBox = new JComboBox<>(genderOptions);
        addLabel(userInformationPanel, "Gender:", constraints, 3);
        constraints.gridx = 1;
        userInformationPanel.add(genderComboBox, constraints);

        dateOfBirthField = addTextField(userInformationPanel, "Date of Birth (DD/MM/YYYY):", constraints, 4);
        countryOfResidenceField = addTextField(userInformationPanel, "Country of Residence:", constraints, 5);
        emailAddressField = addTextField(userInformationPanel, "Email Address:", constraints, 6);
        phoneNumberField = addTextField(userInformationPanel, "Phone Number:", constraints, 7);

        disabilityCheckBox = new JCheckBox("Do you have any disabilities?");
        constraints.gridx = 0;
        constraints.gridy = 8;
        constraints.gridwidth = 2;
        userInformationPanel.add(disabilityCheckBox, constraints);

        JButton nextPageButton2 = createStyledButton("Next");
        nextPageButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "cardPayment");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 2;
        userInformationPanel.add(nextPageButton2, constraints);
        cardPanel.add(userInformationPanel, "userInformation");

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "cruiseSelection");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        userInformationPanel.add(backButton, constraints);
    }

    private void createCardPaymentPanel() {
        cardPaymentPanel = new JPanel(new GridBagLayout());
        cardPaymentPanel.setBorder(BorderFactory.createTitledBorder("Card Payment"));
        GridBagConstraints paymentConstraints = new GridBagConstraints();
        paymentConstraints.fill = GridBagConstraints.HORIZONTAL;
        paymentConstraints.insets = new Insets(10, 10, 10, 10);

        addLabel(cardPaymentPanel, "Please enter your card details", paymentConstraints, 0);

        cardHolder = addTextField(cardPaymentPanel, "Card Holder:", paymentConstraints, 1);
        cardNumber = addTextField(cardPaymentPanel, "Card Number:", paymentConstraints, 2);
        expiryDate = addTextField(cardPaymentPanel, "Expiry Date:", paymentConstraints, 3);
        cvv = addTextField(cardPaymentPanel, "CVV", paymentConstraints, 4);

        JButton submitButton = createStyledButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleFormSubmission();
            }
        });
        paymentConstraints.gridx = 0;
        paymentConstraints.gridy = 9;
        paymentConstraints.gridwidth = 2;
        cardPaymentPanel.add(submitButton, paymentConstraints);
        cardPanel.add(cardPaymentPanel, "cardPayment");

        JButton backButton2 = createStyledButton("Back");
        backButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "userInformation");
            }
        });
        paymentConstraints.gridx = 0;
        paymentConstraints.gridy = 10;
        paymentConstraints.gridwidth = 2;
        cardPaymentPanel.add(backButton2, paymentConstraints);
    }
    //Here i made a method that each button i made uses, to make the theme of the app more unified.
    //In this case i choose a type of blue
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 82, 165));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    private void writeToFile(String data) {
        try (FileWriter writer = new FileWriter("output2.txt", true)) {
            writer.write(data + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "An error occurred while writing to the file: " + ex.getMessage());
        }
    }

    private void handleFormSubmission() {
        writeToFile("Cruise: " + selectedCruise);
        writeToFile("Selected Suite: " + selectedSuite);
        writeToFile("Total Price: R" + totalPrice);

        writeToFile(firstNameField.getText() + " " + lastNameField.getText());
        writeToFile(dateOfBirthField.getText());
        writeToFile(countryOfResidenceField.getText());
        writeToFile(emailAddressField.getText());
        writeToFile(phoneNumberField.getText());

        String gender = (String) genderComboBox.getSelectedItem();
        writeToFile("Gender: " + gender);

        boolean hasDisability = disabilityCheckBox.isSelected();
        if (hasDisability) {
            writeToFile("Disability: Yes");
        } else {
            writeToFile("Disability: No");
        }

        writeToFile("Card Holder: " + cardHolder.getText());
        writeToFile("Card Number: " + cardNumber.getText());
        writeToFile("Expiry Date: " + expiryDate.getText());
        writeToFile("CVV: " + cvv.getText());

        displayConfirmation();
    }

    private void displayConfirmation() {
        String confirmationMessage = "Thank you " + firstNameField.getText() + " " + lastNameField.getText() + ",\n" +
                "You have selected " + selectedCruise + " cruise.\n" +
                "To confirm, your details are as follows:\n" +
                "Born on " + dateOfBirthField.getText() + " from " + countryOfResidenceField.getText() + "\n" +
                "Your email address is " + emailAddressField.getText() + " and your phone number is " + phoneNumberField.getText() + "\n" + "\n" +
                "Your payment details are as follows" + "\n" +
                "Card Holder: " + cardHolder.getText() + "\n" +
                "Card Number: " + cardNumber.getText() + "\n" +
                "Expiry Date: " + expiryDate.getText() + "\n" +
                "CVV: " + cvv.getText() + "\n" +
                "This information is also printed to the text file.";

        JOptionPane.showMessageDialog(frame, confirmationMessage);
    }

    private String getSelectedSuite(JRadioButton standardSuite, JRadioButton deluxeSuite, JRadioButton premiumSuite) {
        if (standardSuite.isSelected()) {
            return "Standard Suite (R9500)";
        } else if (deluxeSuite.isSelected()) {
            return "Deluxe Suite (R15000)";
        } else if (premiumSuite.isSelected()) {
            return "Premium Suite (R22000)";
        }
        return "No Suite Selected";
    }

    private int extractPrice(String option) {
        String[] parts = option.split("\\(R");
        if (parts.length == 2) {
            try {
                return Integer.parseInt(parts[1].replace(")", "").replace(",", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private JTextField addTextField(JPanel panel, String labelText, GridBagConstraints constraints, int gridY) {
        addLabel(panel, labelText, constraints, gridY);
        constraints.gridx = 1;
        JTextField textField = new JTextField(20);
        panel.add(textField, constraints);
        return textField;
    }

    private void addLabel(JPanel panel, String labelText, GridBagConstraints constraints, int gridY) {
        constraints.gridx = 0;
        constraints.gridy = gridY;
        JLabel label = new JLabel(labelText);
        panel.add(label, constraints);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CruiseBookingV4());
    }
}
