package JCommonTools;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;


/**
 *	����� ��������������� ������� ��� ������ � ComboBox. 
 * @author M.Tor
 * 07.10.2009
 */
public class ComboBoxTools 
{
	protected ComboBoxTools()
	{
	}

	/**
	 * ���������� DefaultComboBoxModel ��������� ���� PlaceCodeText �� ������� ���������� �������: 
	 * SELECT [code field], [text field] FROM .... 
	 * @param aCbo - DefaultComboBoxModel
	 * @param aStm - ������� PreparedStatement
	 * @param aIsClearBeforeFill - ���� ����������� ������� �� ��������� ����� �����������: true - ��; false - ���
	 */
	public static void FillPCTextFromPStatement(DefaultComboBoxModel aCbo, PreparedStatement aStm,  boolean aIsClearBeforeFill)
	 throws SQLException
	{
		if (aIsClearBeforeFill)
			aCbo.removeAllElements();
		ResultSet rs = aStm.executeQuery();
		while (rs.next())
			aCbo.addElement(new CodeText(rs.getInt(1),rs.getString(2)));
		rs.close();
		
	}

}
