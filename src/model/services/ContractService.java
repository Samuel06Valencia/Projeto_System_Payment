package model.services;

import entities.Contract;
import entities.Installment;

import java.time.LocalDate;

public class ContractService {
    //Associação/Composição
    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    //Métodos ou Operações:
    public void processContract(Contract contract, int months) {
        double basicQuota = contract.getTotalValue()/ months;
        for (int i = 1; i <= months; i++) {
            //A data de vencimento de cada parcela, é a data original do contrato somada a quantidade de meses da variável "i".
            LocalDate dueDate = contract.getDate().plusMonths(i); //Data do contrato,adicionando os meses,segunda variável "i", por meio da função: "plusMonths".
            double interest = onlinePaymentService.interest(basicQuota, i);
            double fee = onlinePaymentService.paymentFee(basicQuota + interest);
            double quota = basicQuota + interest + fee;

            contract.getInstallments().add(new Installment(dueDate, quota));
        }
    }
}
