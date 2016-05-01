package com.bank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Main {

	public static void main(String[] args) throws Exception {
		List<BankAccount> list = new ArrayList<BankAccount>();
		File file = new File("src/Sample.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			try {
				Document doc = db.parse(file);
				doc.normalize();
				Element root = doc.getDocumentElement();
				NodeList deposits = root.getElementsByTagName("deposit");
				for (int i = 0; i < deposits.getLength(); i++) {
					if (deposits.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element deposit = (Element) deposits.item(i);
						Element ecustomerNumber = (Element) deposit.getElementsByTagName("customerNumber").item(0);
						Element edepositBalance = (Element) deposit.getElementsByTagName("depositBalance").item(0);
						Element edepositType = (Element) deposit.getElementsByTagName("depositType").item(0);
						Element edurationInDays = (Element) deposit.getElementsByTagName("durationInDays").item(0);
						String customerNumber = ecustomerNumber.getTextContent();
						String depositBalance = edepositBalance.getTextContent();
						String depositType = edepositType.getTextContent();
						String durationInDays = edurationInDays.getTextContent();
						DepositType deposittype;
						try {
							deposittype = DepositType.valueOf(depositType);
						} catch (Exception e) {
							throw new Exception("The Entered Deposit Type Is Not Valid.");
						}
						float interest = 0f;
						if (Integer.parseInt(durationInDays) <= 0)
							throw new Exception("The Entered Duration Day Value Is Not Valid");
						if (new BigDecimal(depositBalance).compareTo(new BigDecimal(0)) == -1)
							throw new Exception("The Entered Deposit Balance Value Is Not Valid");
						switch (deposittype) {
						case Qarz:
							interest = 0f;
							break;
						case ShortTerm:
							interest = 10f;
							break;
						case LongTerm:
							interest = 20f;
							break;
						default:
							throw new Exception("The Entered Deposit Type Value Is Not Valid");
						}
						BankAccount ba = new BankAccount(customerNumber, new BigDecimal(depositBalance),
								Integer.parseInt(durationInDays), deposittype, interest);
						list.add(ba);
					}
				}

			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list = list.stream().sorted((a, b) -> {
			return a.calcInterest().compareTo(b.calcInterest());
		}).collect(Collectors.toList());
		File ofile = new File("src/Interest.txt");
		try {
			FileOutputStream fos = new FileOutputStream(ofile);

			list.stream().forEach((x) -> {
				try {
					fos.write((x.toCustomerInterest() + "\r\n").getBytes());

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			fos.flush();
			fos.close();
			System.out.println("Done!");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
