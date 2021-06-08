document.addEventListener('DOMContentLoaded', () => {
    var selectEspecialidad = document.querySelector('#selectEspecialidad')
    var selectMedico = document.querySelector('#selectMedico')

    selectEspecialidad.addEventListener('change', () => {
        axios.get('/proyecto_limpio_spring_war_exploded/api/medicos/' + selectEspecialidad.value)
            .then(response => {
                while (selectMedico.firstChild){
                    selectMedico.removeChild(selectMedico.firstChild)
                }

                response.data.forEach(medico => {
                    var option = document.createElement('option');
                    option.text = medico.persona.apellido
                    option.value = medico.id
                    selectMedico.appendChild(option)
                })
            })
            .catch(error => {
                console.log(error)
            })
    })
})