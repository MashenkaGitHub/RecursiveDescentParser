package package1;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/**
 * File: Parser.java 
 * Author: Maria Tkacheva 
 * Date: April 7, 2018
 * Assignment:Project 1 
 * UMUC CMSC 330 
 * Parser class reads selected file, and builds GUI based on the production
 */
public class Parser {

	private static JFrame errorFrame = new JFrame();
	private static String token;
	private static JFrame frame = null;

	Parser() {

		try {

			Scanner scanner = new Scanner(new File(selectFile()));
			token = scanner.next();
			JFrame frame = (JFrame) addComponent(scanner);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		} catch (FileNotFoundException exp) {
			JOptionPane.showMessageDialog(errorFrame, "Invalid file");
			exp.printStackTrace();
		}
	}

	private static String selectFile() {
		JFileChooser jfc = new JFileChooser(".");
		jfc.setDialogTitle("Please select input file.");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String file = jfc.getSelectedFile().toString();
			return file;
		} else {
			System.exit(0);
		}
		return null;
	}// end method selectFile

	private static Component addComponent(Scanner sc) {
		while (sc.hasNext()) {

			if (token.equalsIgnoreCase("Textfield")) {
				token = sc.next();
				JTextField field = new JTextField(Integer.parseInt(token));
				token = sc.next();
				if (!token.equals(";")) {
					throw new ParserException("Invalid token after Textfield wiget: " + token);
				}
				token = sc.next();
				return field;
			} else if (token.equalsIgnoreCase("Button")) {

				JButton button;
				token = sc.next();
				button = new JButton(token.replaceAll("\"", ""));
				token = sc.next();
				if (!token.equals(";")) {
					throw new ParserException("Invalid token after Button wiget: " + token);
				}
				token = sc.next();
				return button;
			} else if (token.equalsIgnoreCase("Radio")) {

				JRadioButton button;
				token = sc.next();
				button = new JRadioButton(token.replaceAll("\"", ""));
				token = sc.next();
				if (!token.equals(";")) {
					throw new ParserException("Invalid token after Button wiget: " + token);
				}
				token = sc.next();
				return button;
			} else if (token.equalsIgnoreCase("Label")) {
				token = sc.next();
				JLabel Label;
				Label = new JLabel(token.replaceAll("\"", ""));
				token = sc.next();
				if (!token.equals(";")) {
					throw new ParserException("Invalid token after Label wiget: " + token);
				}
				token = sc.next();
				return Label;
			} else if (token.equalsIgnoreCase("Group")) {

				ButtonGroup group = new ButtonGroup();
				token = sc.next();
				boolean groupFlag = true;
				if (token.equalsIgnoreCase("End")) {
					groupFlag = false;

				} else {
					while (groupFlag) {
						group.add((AbstractButton) addComponent(sc));
						if (token.equalsIgnoreCase("End"))
							groupFlag = false;
					}
					token = sc.next();
					if (!token.equals(";")) {
						throw new ParserException("Invalid token after End wiget: " + token);
					}
					token = sc.next();
				}
			} else if (token.equalsIgnoreCase("Panel")) {
				token = sc.next();
				JPanel panel = new JPanel();
				if (token.equalsIgnoreCase("Layout")) {
					token = sc.next();
					if (token.equalsIgnoreCase("flow")) {
						panel.setLayout(new FlowLayout());
						token = sc.next();
						if (!token.equals(":")) {
							throw new ParserException("Invalid token after Layout Type wiget: " + token);
						}
						token = sc.next();
					} // end if
					if (token.equalsIgnoreCase("grid")) {
						token = sc.next();
					}
					if (token.equals("(")) {
						int c = 0;
						int d = 0;
						token = sc.next();
						int a = Integer.parseInt(token);
						sc.next();
						token = sc.next();
						int b = Integer.parseInt(token);
						token = sc.next();
						if (token.equals(",")) {
							token = sc.next();
							c = Integer.parseInt(token);
							sc.next();
							token = sc.next();
							d = Integer.parseInt(token);
							sc.next();
						}
						panel.setLayout(new GridLayout(a, b, c, d));
						token = sc.next();
						if (!token.equals(":")) {
							throw new ParserException("Invalid token after Layout Type wiget: " + token);
						}
						token = sc.next();
					}
				}
				boolean flag = true;
				if (token.equalsIgnoreCase("End")) {
					flag = false;

				} else {
					while (flag) {
						panel.add(addComponent(sc));
						if (token.equalsIgnoreCase("End"))
							flag = false;
					}
					token = sc.next();
					if (!token.equals(";")) {
						throw new ParserException("Invalid token after Label wiget: " + token);
					}
					token = sc.next();
					return panel;
				}
			} else if (token.equalsIgnoreCase("Window")) {
				token = sc.next();
				frame = new JFrame(token.replaceAll("\"", ""));
				token = sc.next();
				if (token.equals("(")) {
					int w = Integer.parseInt(sc.next());
					sc.next();
					int h = Integer.parseInt(sc.next());
					token = sc.next();
					if (token.equals(")")) {
						frame.setSize(w, h);
						token = sc.next();
						if (token.equalsIgnoreCase("Layout")) {
							token = sc.next();
							if (token.equalsIgnoreCase("flow")) {
								token = sc.next();
								frame.setLayout(new FlowLayout());
								if (!token.equals(":")) {
									throw new ParserException("Invalid token after Layout Type wiget: " + token);
								}
								token = sc.next();
							} // end if
							if (token.equalsIgnoreCase("grid")) {
								token = sc.next();
							}
							if (token.equals("(")) {
								int c = 0;
								int d = 0;
								token = sc.next();
								int a = Integer.parseInt(token);
								sc.next();
								token = sc.next();
								int b = Integer.parseInt(token);
								token = sc.next();
								if (!token.equals(")")) {

									c = Integer.parseInt(token);
									token = sc.next();
									d = Integer.parseInt(token);
									sc.next();
								}
								frame.setLayout(new GridLayout(a, b, c, d));
								if (!token.equals(":")) {
									throw new ParserException("Invalid token after Layout Type wiget: " + token);
								}
								token = sc.next();
							}
						}
						if (token.equalsIgnoreCase("End")) {
							return frame;
						} else {
							while (true) {
								if (sc.hasNext() && !(token.equalsIgnoreCase("End"))) {
									frame.add(addComponent(sc));
									if (token.equals("End")) {
										token = sc.next();

										if (token.equals("."))
											return frame;
									}
								}

							}
						}
					} else {
						JOptionPane.showMessageDialog(errorFrame, "Invalid token" + token);
					}
				}

			} else if (token.equalsIgnoreCase("end")) {
				return frame;
			} else if (token.equalsIgnoreCase("panel")) {
				token = sc.next();
				JPanel pnel = new JPanel();

				if (token.equalsIgnoreCase("Layout")) {
					token = sc.next();
					if (token.equalsIgnoreCase("flow")) {
						pnel.setLayout(new FlowLayout());
						sc.next();
					} else if (token.equalsIgnoreCase("grid")) {
						token = sc.next();
					}
					if (token.equals("(")) {
						int c = 0;
						int d = 0;
						token = sc.next();
						int a = Integer.parseInt(token);
						sc.next();
						token = sc.next();
						int b = Integer.parseInt(token);
						token = sc.next();
						if (!token.equals(")")) {

							c = Integer.parseInt(token);
							token = sc.next();
							d = Integer.parseInt(token);
						}
						pnel.setLayout(new GridLayout(a, b, c, d));
					}

				}

			} else {
				throw new ParserException("Invalid token "+token);
			}
		}
		return null;

	}
}
