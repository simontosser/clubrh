/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { CvthequeTestModule } from '../../../test.module';
import { RecrutementProfilDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil-delete-dialog.component';
import { RecrutementProfilService } from '../../../../../../main/webapp/app/entities/recrutement-profil/recrutement-profil.service';

describe('Component Tests', () => {

    describe('RecrutementProfil Management Delete Component', () => {
        let comp: RecrutementProfilDeleteDialogComponent;
        let fixture: ComponentFixture<RecrutementProfilDeleteDialogComponent>;
        let service: RecrutementProfilService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CvthequeTestModule],
                declarations: [RecrutementProfilDeleteDialogComponent],
                providers: [
                    RecrutementProfilService
                ]
            })
            .overrideTemplate(RecrutementProfilDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RecrutementProfilDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecrutementProfilService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
