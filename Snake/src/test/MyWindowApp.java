package test;
import javax.swing.JFrame;

public class MyWindowApp extends JFrame { 
	//�������� �� JFrame �� �������� ��� ���������������� ����

  public MyWindowApp(){
    super("My First Window"); //��������� ����
    setBounds(100, 100, 200, 200); //���� �� ��������� 
                                   //������ � ��������� 
                                   //�� ���� ����� ������ � ����������
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //��� ����� ��� ���� ����� ��� 
                                                    //�������� ���� ����������� � ���������,
                                                    //����� ��� ��������� ������ � ���������
  }

  /*public static void main(String[] args) { //��� ������� ����� ���� � � ������ ������
    MyWindowApp app = new MyWindowApp(); //������� ��������� ������ ����������
    app.setVisible(true); //� ����� ������� ���������� ��������!
  }*/
}