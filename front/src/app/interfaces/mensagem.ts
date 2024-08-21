export interface Mensagem {
    chat: {
      id: number
    };
    remetente: {
      id: number,
      username: string
    };
    destinatario: {
      id: number,
      username: string
    };
    conteudo: string;
  }