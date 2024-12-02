package br.ufpr.webII.trabalhoFinal.domain.address;

public record AddressDTO(
        String street,
        String number,
        String district,
        String city,
        String uf,
        String cep) {
    public AddressDTO(Address address) {
        this(
                address.getStreet(),
                Integer.toString(address.getNumber()),
                address.getDistrict(),
                address.getCity(),
                address.getUf(),
                address.getCep()
        );
    }
}