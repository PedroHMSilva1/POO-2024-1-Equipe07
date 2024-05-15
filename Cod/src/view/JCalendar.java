package view;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Locale;

class JCalendar extends JPanel {
    private final JLabel monthLabel;
    private final JButton[] dayButtons;
    private final Calendar calendar;

    public JCalendar() {
        setLayout(new BorderLayout());
        calendar = Calendar.getInstance(Locale.getDefault());

        // Etiqueta "Data do Evento"
        JLabel eventDateLabel = new JLabel("Data do Evento", SwingConstants.CENTER);
        eventDateLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Define a fonte e o tamanho da etiqueta
        add(eventDateLabel, BorderLayout.NORTH); // Adiciona a etiqueta no topo do painel do calendário

        // Cabeçalho do calendário com o mês e ano atual
        monthLabel = new JLabel("", SwingConstants.CENTER);
        updateMonthLabel();
        add(monthLabel, BorderLayout.CENTER);

        // Painel para os dias da semana
        JPanel weekDaysPanel = new JPanel(new GridLayout(0, 7));
        String[] weekDays = { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb" };
        for (String day : weekDays) {
            weekDaysPanel.add(new JLabel(day, SwingConstants.CENTER));
        }
        add(weekDaysPanel, BorderLayout.SOUTH);

        // Painel para os botões dos dias
        JPanel daysPanel = new JPanel(new GridLayout(0, 7));
        dayButtons = new JButton[42]; // 6 semanas * 7 dias = 42 botões
        for (int i = 0; i < dayButtons.length; i++) {
            dayButtons[i] = new JButton();
            dayButtons[i].setEnabled(false);
            daysPanel.add(dayButtons[i]);
        }
        add(daysPanel, BorderLayout.SOUTH);

        updateCalendar();
    }

    private void updateMonthLabel() {
        String monthText = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " "
                + calendar.get(Calendar.YEAR);
        monthLabel.setText(monthText);
    }

    private void updateCalendar() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int weeks = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

        clearCalendar();

        // Preenche os botões com os dias do mês
        for (int i = 1, dayIndex = startDay - 1; i <= numberOfDays; i++, dayIndex++) {
            dayButtons[dayIndex].setText(String.valueOf(i));
            dayButtons[dayIndex].setEnabled(true);
        }
    }

    private void clearCalendar() {
        for (JButton dayButton : dayButtons) {
            dayButton.setText("");
            dayButton.setEnabled(false);
        }
    }
}
