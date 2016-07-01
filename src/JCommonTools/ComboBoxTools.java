package JCommonTools;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


/**
 *	Набор вспомогательных функций для работы с ComboBox. 
 * @author M.Tor
 * 07.10.2009
 */
public class ComboBoxTools 
{
	protected ComboBoxTools()
	{
	}

	/**
	 * Заполнение DefaultComboBoxModel объектами типа PlaceCodeText из запроса следующего формата: 
	 * SELECT [code field], [text field] FROM .... 
	 * @param aCbo - DefaultComboBoxModel
	 * @param aStm - команда PreparedStatement
	 * @param aIsClearBeforeFill - флаг указывающий очищать ли коллекцию перед заполнением: true - да; false - нет
	 */
	public static void FillPCTextFromPStatement(DefaultComboBoxModel aCbo, PreparedStatement aStm,  boolean aIsClearBeforeFill)
	 throws SQLException
	{
		if (aIsClearBeforeFill)
			aCbo.removeAllElements();
		ResultSet rs = aStm.executeQuery();

		while (rs.next())
		{
			aCbo.addElement(new CodeText(rs.getInt(1),rs.getString(2)));
		}
		
		rs.close();
		
	}
	
	public static void SetSelected2Code(JComboBox<CodeText> aCbo, int aCode)
	{
		CodeText ct = null;
		for (int ii=0; ii < aCbo.getItemCount(); ii++ )
		{
			ct = aCbo.getItemAt(ii);
			if (ct.code == aCode)
			{
				aCbo.setSelectedIndex(ii);
				break;
			}
		}
	}

}
